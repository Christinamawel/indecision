package com.example.indecision

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private var finalQuestion: TextView? = null
    private var finalAnswer: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_2)

        val finalQuestionObject = intent.getSerializableExtra("EXTRA_QUESTION") as Question
        val question = finalQuestionObject.question
        val answer = finalQuestionObject.pickedAnswer
        finalQuestion = findViewById(R.id.finalQuestion)
        finalAnswer = findViewById(R.id.finalAnswer)
        val backButton: Button = findViewById(R.id.backButton)

        finalAnswer?.text = answer
        finalQuestion?.text = question

        backButton.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}