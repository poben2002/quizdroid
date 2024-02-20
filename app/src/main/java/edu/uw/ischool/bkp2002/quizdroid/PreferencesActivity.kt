package edu.uw.ischool.bkp2002.quizdroid

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

            Toast.makeText(this, "Preferences saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}