package edu.uw.ischool.bkp2002.quizdroid

import android.app.Application
import android.content.Context
import java.util.concurrent.TimeUnit
import android.util.Log



class QuizApp : Application() {
    companion object {
        lateinit var repository: TopicRepository
            private set
    }

    override fun onCreate() {
        super.onCreate()
        repository = CurrentTopicRepository(this)

        Log.i("QuizApp", "fileDir=$filesDir" )
        Log.d("QuizApp", "QuizApp is running!")
    }

}