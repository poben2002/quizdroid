package edu.uw.ischool.bkp2002.quizdroid

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.provider.Settings
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
class DownloadWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val sharedPreferences = applicationContext.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
            val urlString = sharedPreferences.getString("URL", "defaultURL") ?: "defaultURL"

            applicationContext.sendBroadcast(Intent("DOWNLOAD_ATTEMPT"))

            if (!isNetworkAvailable() || isAirplaneModeOn()) {
                return@withContext Result.retry()
            }

            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                throw IOException("HTTP error code: ${connection.responseCode}")
            }

            val inputStream = connection.inputStream
            val tempFile = File(applicationContext.filesDir, "questions_temp.json")

            FileOutputStream(tempFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }

            val finalFile = File(applicationContext.filesDir, "questions.json")
            if (!tempFile.renameTo(finalFile)) {
                throw IOException("Failed to save downloaded file")
            }

            Log.d("DownloadWorker", "Download complete and file saved")
            applicationContext.sendBroadcast(Intent("DATA_UPDATED"))
            Result.success()
        } catch (e: Exception) {
            Log.e("DownloadWorker", "Download error: ${e.message}")
            applicationContext.sendBroadcast(Intent("DOWNLOAD_FAILED"))
            Result.failure()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo?.isConnectedOrConnecting == true
    }

    private fun isAirplaneModeOn(): Boolean {
        return Settings.Global.getInt(applicationContext.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    }
}