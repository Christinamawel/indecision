package com.example.indecision

import com.google.firebase.auth.FirebaseUser
import java.io.Serializable

data class Question (
    val question: String = "",
    val answers: MutableList<String> = mutableListOf("no outcomes for this decision"),
    val pickedAnswer: String = "",
    val user: String? = null
) :Serializable