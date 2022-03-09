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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class QuestionFormActivity : AppCompatActivity() {

    private lateinit var customAdapter: CustomAdapter
    private var etAddOption: EditText? = null
    private var etEditQuestion: EditText? = null
    private var tvQuestion: TextView? = null

    lateinit var auth: FirebaseAuth
    private val questionCollectionRef = Firebase.firestore.collection("questions")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        customAdapter = CustomAdapter(mutableListOf())

        val recyclerview = findViewById<RecyclerView>(R.id.rvOptionList)
        val addOptionBtn: Button = findViewById(R.id.addOptionBtn)
        val addQuestionBtn: Button = findViewById(R.id.addQuestionBtn)
        val saveAndRollBtn: Button = findViewById(R.id.saveAndRollBtn)
        etAddOption = findViewById(R.id.etAddOption)
        etEditQuestion = findViewById(R.id.etEditQuestion)
        tvQuestion = findViewById(R.id.tvQuestion)

        auth = FirebaseAuth.getInstance()

        recyclerview.adapter = customAdapter

        recyclerview.layoutManager = LinearLayoutManager(this)

        addOptionBtn.setOnClickListener {
            val optionText = etAddOption?.text.toString()
            if (optionText.isNotEmpty()) {
                val option = OptionViewModel(optionText, false)
                customAdapter.addOption(option)
                etAddOption?.text?.clear()
            }
        }

        addQuestionBtn.setOnClickListener {
            val newQuestion = etEditQuestion?.text.toString()
            tvQuestion?.text = newQuestion
            etEditQuestion?.text?.clear()
        }

        saveAndRollBtn.setOnClickListener {
            val currentOptions = mutableListOf<String>()
            for (option in customAdapter.mList) {
                currentOptions.add(option.text)
            }

            val pickedNumber = (0 until currentOptions.size).random()

            val pickedAnswer = currentOptions[pickedNumber]
            val currentQuestion = tvQuestion?.text.toString()
            val currentUser = auth.currentUser?.toString()

            val newQuestion = Question(currentQuestion, currentOptions, pickedAnswer, currentUser)

            saveQuestion(newQuestion)

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

    private fun saveQuestion(question: Question) = CoroutineScope(Dispatchers.IO).launch {
        try {
            if(question.user != null) {
                questionCollectionRef.add(question).await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@QuestionFormActivity, "Successfully saved data", Toast.LENGTH_LONG).show()
                }
            }
        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@QuestionFormActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
