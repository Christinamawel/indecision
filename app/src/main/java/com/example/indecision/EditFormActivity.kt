package com.example.indecision

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class EditFormActivity : AppCompatActivity() {

    private lateinit var customAdapter: CustomAdapter
    private var etAddOption: EditText? = null
    private var etEditQuestion: EditText? = null
    private var tvQuestion: TextView? = null

    lateinit var auth: FirebaseAuth
    private val questionCollectionRef = Firebase.firestore.collection("questions")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val questionExtra = intent.getSerializableExtra("EXTRA_QUESTION") as Question
        val passedAnswers = questionExtra.answers
        val passedQuestion = questionExtra.question
        val recyclerview = findViewById<RecyclerView>(R.id.rvOptionList)
        val addOptionBtn: Button = findViewById(R.id.addOptionBtn)
        val addQuestionBtn: Button = findViewById(R.id.addQuestionBtn)
        val saveAndRollBtn: Button = findViewById(R.id.saveAndRollBtn)
        val deleteBtn: Button = findViewById(R.id.deleteBtn)
        etAddOption = findViewById(R.id.etAddOption)
        etEditQuestion = findViewById(R.id.etEditQuestion)
        tvQuestion = findViewById(R.id.tvQuestion)

        auth = FirebaseAuth.getInstance()

        var passedOptions = mutableListOf<OptionViewModel>()
        for(answer in passedAnswers) {
            val newOption = OptionViewModel(answer)
            passedOptions.add(newOption)
        }

        customAdapter = CustomAdapter(passedOptions)
        recyclerview.adapter = customAdapter
        recyclerview.layoutManager = LinearLayoutManager(this)

        tvQuestion?.text = passedQuestion

        addOptionBtn.setOnClickListener {
            val optionText = etAddOption?.text.toString()
            if (optionText.isNotEmpty()) {
                val option = OptionViewModel(optionText)
                customAdapter.addOption(option)
                etAddOption?.text?.clear()
            }
        }

        addQuestionBtn.setOnClickListener {
            val newQuestion = etEditQuestion?.text.toString()
            tvQuestion?.text = newQuestion
            etEditQuestion?.text?.clear()
        }

        deleteBtn.setOnClickListener {
            deleteQuestion(questionExtra)
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }

        saveAndRollBtn.setOnClickListener {
            val currentOptions = mutableListOf<String>()
            for (option in customAdapter.mList) {
                currentOptions.add(option.text)
            }

            val pickedNumber = (0 until currentOptions.size).random()

            val pickedAnswer = currentOptions[pickedNumber]
            val currentQuestion = tvQuestion?.text.toString()
            val currentUser = auth.currentUser?.email.toString()

            val newQuestion = Question(currentQuestion, currentOptions, pickedAnswer, currentUser)

            val newQuestionMap = mapOf("question" to currentQuestion, "answers" to currentOptions, "pickedAnswer" to pickedAnswer)

            updateQuestion(questionExtra, newQuestionMap)

            hideKeyboard()

            Intent(this, SecondActivity::class.java).also {
                it.putExtra("EXTRA_QUESTION", newQuestion)
                startActivity(it)
            }
        }
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun updateQuestion(question: Question, chosenOptionMap: Map<String, Any>) = CoroutineScope(Dispatchers.IO).launch {
        val questionQuery = questionCollectionRef
            .whereEqualTo("question", question.question)
            .whereEqualTo("answers", question.answers)
            .whereEqualTo("pickedAnswer", question.pickedAnswer)
            .whereEqualTo("user", question.user)
            .get()
            .await()
        if (questionQuery.documents.isNotEmpty()) {
            for (document in questionQuery) {
                try {
                    questionCollectionRef.document(document.id).set(
                        chosenOptionMap,
                        SetOptions.merge()
                    ).await()
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@EditFormActivity, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun deleteQuestion(question: Question) = CoroutineScope(Dispatchers.IO).launch {
        val questionQuery = questionCollectionRef
            .whereEqualTo("question", question.question)
            .whereEqualTo("answers", question.answers)
            .whereEqualTo("pickedAnswer", question.pickedAnswer)
            .whereEqualTo("user", question.user)
            .get()
            .await()
        if (questionQuery.documents.isNotEmpty()) {
            for (document in questionQuery) {
                try {
                    questionCollectionRef.document(document.id).delete().await()
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@EditFormActivity, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}