package com.layon.quicksvideointeractionandroidapp.data

import com.layon.quicksvideointeractionandroidapp.model.QuickVideosListModel
import com.layon.quicksvideointeractionandroidapp.network.QuicksVideoApiService

/**
 * Repository that fetch quick video list from pexel Api.
 */
interface QuicksVideoRepository {
    suspend fun getSearchedQuickVideos(): QuickVideosListModel
}

/**
 * Network Implementation of Repository that fetch quick video list from pexel Api.
 */
class NetworkQuicksVideoRepository(
    private val quicksVideoApiService: QuicksVideoApiService
) : QuicksVideoRepository {
    /** Fetches list of quick videos from pexel api*/
    override suspend fun getSearchedQuickVideos(): QuickVideosListModel = quicksVideoApiService.getSearchedQuickVideos()
}