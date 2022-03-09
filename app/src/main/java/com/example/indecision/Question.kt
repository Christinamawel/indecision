package com.example.indecision

import com.google.firebase.auth.FirebaseUser
import java.io.Serializable

data class Question (
    val question: String,
    val answers: MutableList<String>,
    val pickedAnswer: String,
    val user: String?
) :Serializable