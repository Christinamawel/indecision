package com.example.indecision

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private var question: EditText? = null
    private var answerOne: EditText? = null
    private var answerTwo: EditText? = null
    private var answerThree: EditText? = null
    private var answerFour: EditText? = null
    private var answerMain: TextView? =null
    private var questionMain: TextView? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        question = findViewById(R.id.question)
        answerOne = findViewById(R.id.answerOne)
        answerTwo = findViewById(R.id.answerTwo)
        answerThree = findViewById(R.id.answerThree)
        answerFour = findViewById(R.id.answerFour)
        answerMain = findViewById(R.id.answerMain)
        questionMain = findViewById(R.id.questionMain)
        val submitButton: Button = findViewById(R.id.submitButton)

        submitButton.setOnClickListener {
            onClickAnswerRandomizer()
        }
    }

    private fun onClickAnswerRandomizer() {
        val one = answerOne?.getText().toString()
        val two = answerTwo?.getText().toString()
        val three = answerThree?.getText().toString()
        val four = answerFour?.getText().toString()
        val userQuestion = question?.getText().toString()

        val answerArray = arrayOf(one, two, three, four)
        val pickedNumber = (0..3).random()

        question?.setText("")
        answerOne?.setText("")
        answerTwo?.setText("")
        answerThree?.setText("")
        answerFour?.setText("")

        questionMain?.text = userQuestion
        hideKeyboard()
        setContentView(R.layout.view_2)
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}