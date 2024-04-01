package com.layon.quicksvideointeractionandroidapp.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.layon.quicksvideointeractionandroidapp.network.QuicksVideoApiService
import com.layon.quicksvideointeractionandroidapp.util.KEY_QUICK_VIDEOS_REQUESTED_URL
import com.layon.quicksvideointeractionandroidapp.util.getBestVideoFileQualityLink
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@HiltWorker
class GetQuickVideoFromApiRestWorker @AssistedInject constructor(
      @Assisted context: Context,
      @Assisted params: WorkerParameters,
      private val quicksVideoApiService: QuicksVideoApiService,
      ) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                delay(2000)
                val quickVideosListResponse = quicksVideoApiService.getSearchedQuickVideos()
                if(quickVideosListResponse.isSuccessful && quickVideosListResponse.body()?.videos?.size != 0) {
                    val videoFiles = quickVideosListResponse.body()?.videos?.flatMap { it.videoFiles } as ArrayList
                    val bestVideoUrl = getBestVideoFileQualityLink(videoFiles)
                    Log.d("layonf", "QuickVideoApiRestWorker doWork() bestVideoUrl : $bestVideoUrl")
                    val outputData = workDataOf(KEY_QUICK_VIDEOS_REQUESTED_URL to bestVideoUrl)
                    Result.success(outputData)
                } else {
                    val error = quickVideosListResponse.errorBody()
                    Log.e("layonf", "QuickVideoApiRestWorker is not quickVideosListResponse.isSuccessful or is empty : $error")
                    Result.failure()
                }
            } catch (throwable: Throwable) {
                Log.e("layonf", "$throwable")
                Result.failure()
            }
        }
    }

}