package com.example.indecision

import java.io.Serializable

data class Question (
    val question: String,
    val answers: MutableList<String>,
    val pickedAnswer: String
) :Serializable