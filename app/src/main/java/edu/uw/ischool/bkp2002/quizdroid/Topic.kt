package edu.uw.ischool.bkp2002.quizdroid

data class Topic (
    val title: String,
    val shortDescription: String,
    val longDescription: String,
    val quizzes: List<Quiz>
)
