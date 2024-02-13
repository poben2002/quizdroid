package edu.uw.ischool.bkp2002.quizdroid

data class Quiz (
    val text: String,
    val options: List<String>,
    val correctAnswer: Int
)

