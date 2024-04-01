package com.layon.quicksvideointeractionandroidapp.data

import com.layon.quicksvideointeractionandroidapp.model.QuickVideosListModel
import com.layon.quicksvideointeractionandroidapp.network.QuicksVideoApiService

/**
 * Repository that fetch quick video list from pexel Api.
 */
interface QuicksVideoRepository {

    suspend fun getNewQuickVideosFromApiAndSaveDataBase(
        query: String,
        page: Int
    )
}