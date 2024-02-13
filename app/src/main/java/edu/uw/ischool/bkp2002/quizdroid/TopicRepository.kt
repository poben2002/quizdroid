package edu.uw.ischool.bkp2002.quizdroid

interface TopicRepository {
    fun getAllTopics(): List<Topic>
    fun getTopicByName(name: String): Topic?
    fun addTopic(topic: Topic)
}