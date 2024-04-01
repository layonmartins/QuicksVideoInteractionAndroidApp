package com.layon.quicksvideointeractionandroidapp.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.layon.quicksvideointeractionandroidapp.util.KEY_QUICK_VIDEOS_REQUESTED_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class SaveQuickVideosInMemoryWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                delay(2000)
                val bestVideoUrl = inputData.getString(KEY_QUICK_VIDEOS_REQUESTED_URL)
                Log.d("layonf", "SaveQuickVideosInMemoryWorker: $bestVideoUrl")
                Result.success()
            } catch (exception: Exception) {
                Log.e("Error", "exception")
                Result.failure()
            }

        }

    }

}