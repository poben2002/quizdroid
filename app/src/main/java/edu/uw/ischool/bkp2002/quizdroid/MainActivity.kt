package edu.uw.ischool.bkp2002.quizdroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.content.Intent

// Imports needed later? Maybe in other file... temp for now
//import android.view.View
//import android.widget.Button
//import android.widget.TextView
//import android.widget.RadioButton
//import android.widget.RadioGroup
//import androidx.activity.addCallback

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val topicsListView: ListView = findViewById(R.id.topicListView)
        val topicsList = AllQuestions.topics.keys.toList()

        topicsListView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, topicsList)
        topicsListView.setOnItemClickListener { _, _, position, _ ->
            val selectedTopic = topicsList[position]
            val intent = Intent(this, TopicOverviewActivity::class.java)
            intent.putExtra("selectedTopic", selectedTopic)
            startActivity(intent)
        }
    }
}