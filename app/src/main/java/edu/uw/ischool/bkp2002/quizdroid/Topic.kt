package edu.uw.ischool.bkp2002.quizdroid

data class Topic (
    val shortDescription: String,
    val longDescription: String,
    val quizzes: List<Quiz>,
    val title: String
)
