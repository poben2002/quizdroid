package edu.uw.ischool.bkp2002.quizdroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.withContext
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var topicsListView: ListView

    private val dataUpdateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            refreshTopicList()
        }
    }

    private val failureReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            showDownloadFailureDialog()
        }
    }

    private val downloadStatusReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                "DOWNLOAD_ATTEMPT" -> showToast("Download attempt started")
                "DATA_UPDATED" -> showToast("Download succeeded")
                "DOWNLOAD_FAILED" -> showToast("Download failed")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        topicsListView = findViewById(R.id.topicListView)
        refreshTopicList()

        topicsListView.setOnItemClickListener { _, _, position, _ ->
            val selectedTopic = topicsListView.adapter.getItem(position) as String
            val intent = Intent(this, TopicOverviewActivity::class.java).apply {
                putExtra("selectedTopic", selectedTopic)
            }
            startActivity(intent)
        }

        registerReceiver(dataUpdateReceiver, IntentFilter("DATA_UPDATED"))
        registerReceiver(failureReceiver, IntentFilter("DOWNLOAD_FAILED"))

        IntentFilter().apply {
            addAction("DOWNLOAD_ATTEMPT")
            addAction("DATA_UPDATED")
            addAction("DOWNLOAD_FAILED")
        }.also { filter -> registerReceiver(downloadStatusReceiver, filter) }

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        refreshTopicList()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(dataUpdateReceiver)
        unregisterReceiver(failureReceiver)
        unregisterReceiver(downloadStatusReceiver)
    }

    private fun refreshTopicList() {
        lifecycleScope.launch {
            QuizApp.repository.fetchTopics()

            val topicsList = QuizApp.repository.getAllTopics().map { it.title }

            withContext(Dispatchers.Main) {
                topicsListView.adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, topicsList)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_preferences -> {
                startActivity(Intent(this, PreferencesActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDownloadFailureDialog() {
        AlertDialog.Builder(this)
            .setTitle("Download Failed")
            .setMessage("Would you like to retry the download or exit?")
            .setPositiveButton("Retry") { _, _ ->
                retryDownload()
            }
            .setNegativeButton("Exit") { _, _ ->
                finish()
            }
            .show()
    }

    private fun retryDownload() {
        val sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val downloadFrequency = sharedPreferences.getInt("DownloadFrequency", 60)
        PreferencesActivity().scheduleDownloadWorker(downloadFrequency)
    }
}