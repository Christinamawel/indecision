package com.example.indecision

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    private var question: EditText? = null
    private var answerOne: EditText? = null
    private var answerTwo: EditText? = null
    private var answerThree: EditText? = null
    private var answerFour: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        question = findViewById(R.id.question)
        answerOne = findViewById(R.id.answerOne)
        answerTwo = findViewById(R.id.answerTwo)
        answerThree = findViewById(R.id.answerThree)
        answerFour = findViewById(R.id.answerFour)
        val submitButton: Button = findViewById(R.id.submitButton)

//        permissionsBtn.setOnClickListener {
//            requestPermissions()
//        }

        submitButton.setOnClickListener {
            val one = answerOne?.getText().toString()
            val two = answerTwo?.getText().toString()
            val three = answerThree?.getText().toString()
            val four = answerFour?.getText().toString()
            val userQuestion = question?.getText().toString()

            val answerList= mutableListOf<String>(one, two, three, four)
            val pickedNumber = (0..3).random()

            val pickedAnswer = answerList[pickedNumber]

            val newQuestion = Question(userQuestion, answerList, pickedAnswer)

            onClickAnswerRandomizer()
            Intent(this, SecondActivity::class.java).also{
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

//    private fun hasWriteExternalStoragePermission() =
//        ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

//    private fun requestPermissions() {
//        var permissionsToRequest = mutableListOf<String>()
//        if(!hasWriteExternalStoragePermission()) {
//            permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//        }
//
//        if(permissionsToRequest.isNotEmpty()) {
//            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), 0)
//        }
//    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if(requestCode == 0 && grantResults.isNotEmpty()) {
//            for(i in grantResults.indices) {
//                if(grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                    Log.d("PermissionsRequest", "${permissions[i]} granted")
//                }
//            }
//        }
//    }
}