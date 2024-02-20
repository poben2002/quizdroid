package edu.uw.ischool.bkp2002.quizdroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter

class TopicOverviewActivity : AppCompatActivity() {

    private var selectedTopicKey: String? = null

    private lateinit var tvTopicName: TextView
    private lateinit var tvTopicDescription: TextView
    private lateinit var tvTotalQuestions: TextView

    private val dataUpdateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            refreshTopicData()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_overview)

        selectedTopicKey = intent.getStringExtra("selectedTopic")

        tvTopicName = findViewById(R.id.tvTopicName)
        tvTopicDescription = findViewById(R.id.tvTopicDescription)
        tvTotalQuestions = findViewById(R.id.tvTotalQuestions)
        val btnBegin: Button = findViewById(R.id.btnBegin)

        refreshTopicData()

        btnBegin.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("selectedTopic", selectedTopicKey)
            intent.putExtra("questionIndex", 0)
            intent.putExtra("correctCount", 0)
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(dataUpdateReceiver, IntentFilter("DATA_UPDATED"))
        refreshTopicData()
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(dataUpdateReceiver)
    }

    private fun refreshTopicData() {
        selectedTopicKey?.let { key ->
            lifecycleScope.launch {
                val selectedTopic = withContext(Dispatchers.IO) {
                    QuizApp.repository.getTopicByName(key)
                }

                // Update UI on the main thread
                withContext(Dispatchers.Main) {
                    updateUI(selectedTopic)
                }
            }
        }
    }

    private fun updateUI(topic: Topic?) {
        tvTopicName.text = topic?.title ?: "Topic not found"
        tvTopicDescription.text = topic?.shortDescription ?: "No description available"
        tvTotalQuestions.text = getString(R.string.total_questions_text, topic?.quizzes?.size ?: 0)
    }
}