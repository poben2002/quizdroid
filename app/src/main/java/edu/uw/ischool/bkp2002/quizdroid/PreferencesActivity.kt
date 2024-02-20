package edu.uw.ischool.bkp2002.quizdroid

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class PreferencesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        val etUrl: EditText = findViewById(R.id.etUrl)
        val etDownloadFrequency: EditText = findViewById(R.id.etDownloadFrequency)
        val btnSavePreferences: Button = findViewById(R.id.btnSavePreferences)

        val sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        etUrl.setText(sharedPreferences.getString("URL", ""))
        etDownloadFrequency.setText(sharedPreferences.getInt("DownloadFrequency", 60).toString())

        btnSavePreferences.setOnClickListener {
            val url = etUrl.text.toString()
            val downloadFrequency = etDownloadFrequency.text.toString().toIntOrNull() ?: 60

            with(sharedPreferences.edit()) {
                putString("URL", url)
                putInt("DownloadFrequency", downloadFrequency)
                apply()
            }

            checkNetworkAndProceed(downloadFrequency)
        }
    }

    private fun checkNetworkAndProceed(downloadFrequency: Int) {
        if (isAirplaneModeOn()) {
            promptForAirplaneMode()
        } else if (!isNetworkAvailable()) {
            Toast.makeText(this, "No Internet connection available", Toast.LENGTH_LONG).show()
        } else {
            scheduleDownloadWorker(downloadFrequency)
            Toast.makeText(this, "Preferences saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo?.isConnectedOrConnecting == true
    }

    private fun isAirplaneModeOn(): Boolean {
        return Settings.Global.getInt(contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    }

    private fun promptForAirplaneMode() {
        AlertDialog.Builder(this)
            .setTitle("Airplane Mode Active")
            .setMessage("Airplane mode is currently on. Would you like to turn it off?")
            .setPositiveButton("Yes") { _, _ ->
                startActivity(Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS))
            }
            .setNegativeButton("No", null)
            .show()
    }

    fun scheduleDownloadWorker(frequency: Int) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val downloadRequest = PeriodicWorkRequestBuilder<DownloadWorker>(
            frequency.toLong(), TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "downloadWork",
            ExistingPeriodicWorkPolicy.REPLACE,
            downloadRequest
        )
    }
}