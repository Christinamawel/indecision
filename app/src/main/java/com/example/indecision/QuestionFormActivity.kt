package com.example.indecision

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class QuestionFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val recyclerview = findViewById<RecyclerView>(R.id.rvOptionList)

        recyclerview.layoutManager = LinearLayoutManager(this)

        val options = ArrayList<OptionViewModel>()

        val adapter = CustomAdapter(options)

        recyclerview.adapter = adapter

//        submitButton.setOnClickListener {
//
//            val answerList = mutableListOf<String>(one, two, three, four)
//            val pickedNumber = (0..3).random()
//
//            val pickedAnswer = answerList[pickedNumber]
//
//            val newQuestion = Question(userQuestion, answerList, pickedAnswer)
//
//            onClickAnswerRandomizer()
//            Intent(this, SecondActivity::class.java).also {
//                it.putExtra("EXTRA_QUESTION", newQuestion)
//                startActivity(it)
//            }
//        }
//    }
//    private fun onClickAnswerRandomizer() {
//        question?.setText("")
//        answerOne?.setText("")
//        answerTwo?.setText("")
//        answerThree?.setText("")
//        answerFour?.setText("")
//
//        hideKeyboard()
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
}
