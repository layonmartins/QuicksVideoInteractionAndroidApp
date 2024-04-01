package com.layon.quicksvideointeractionandroidapp.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.layon.quicksvideointeractionandroidapp.util.QUICK_VIDEOS_REQUESTED_URL_WORK_NAME
import com.layon.quicksvideointeractionandroidapp.worker.GetQuickVideoFromApiRestWorker
import com.layon.quicksvideointeractionandroidapp.worker.SaveQuickVideosInMemoryWorker

class QuickVideosWorkManagerRepository(
    val context: Context,
): QuicksVideoRepository {

    //private val workManager = WorkManager.getInstance(context)

    /**
     * In this method we will make a sequence of three worker
     * 1 - Get the list of videos from API
     * 2 - Save the list of videos inside the smartphone storage
     * 3 - Save the list of videoUri in DataBase
     * TODO 4 - inside homeScreenViwModel get the list videos from database
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getNewQuickVideosFromApiAndSaveDataBase(query: String, page: Int) {

        // TODO test constraints
        /* val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()*/

        WorkManager.getInstance(context).beginUniqueWork(
            QUICK_VIDEOS_REQUESTED_URL_WORK_NAME,
            ExistingWorkPolicy.APPEND_OR_REPLACE, //Test with APPEND too,
            OneTimeWorkRequest.from(GetQuickVideoFromApiRestWorker::class.java)
        ).then(OneTimeWorkRequestBuilder<SaveQuickVideosInMemoryWorker>().build()).enqueue()
    }

}