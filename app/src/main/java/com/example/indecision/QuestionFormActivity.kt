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

class QuestionFormActivity : AppCompatActivity() {
    private var question: EditText? = null
    private var answerOne: EditText? = null
    private var answerTwo: EditText? = null
    private var answerThree: EditText? = null
    private var answerFour: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        question = findViewById(R.id.question)
        answerOne = findViewById(R.id.answerOne)
        answerTwo = findViewById(R.id.answerTwo)
        answerThree = findViewById(R.id.answerThree)
        answerFour = findViewById(R.id.answerFour)
        val submitButton: Button = findViewById(R.id.submitButton)

        submitButton.setOnClickListener {
            val one = answerOne?.getText().toString()
            val two = answerTwo?.getText().toString()
            val three = answerThree?.getText().toString()
            val four = answerFour?.getText().toString()
            val userQuestion = question?.getText().toString()

            val answerList = mutableListOf<String>(one, two, three, four)
            val pickedNumber = (0..3).random()

            val pickedAnswer = answerList[pickedNumber]

            val newQuestion = Question(userQuestion, answerList, pickedAnswer)

            onClickAnswerRandomizer()
            Intent(this, SecondActivity::class.java).also {
                it.putExtra("EXTRA_QUESTION", newQuestion)
                startActivity(it)
            }
        }
    }
    private fun onClickAnswerRandomizer() {
        question?.setText("")
        answerOne?.setText("")
        answerTwo?.setText("")
        answerThree?.setText("")
        answerFour?.setText("")

        hideKeyboard()
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
