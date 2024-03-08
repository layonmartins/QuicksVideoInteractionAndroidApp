package com.layon.quicksvideointeractionandroidapp.network

import com.layon.quicksvideointeractionandroidapp.model.QuickVideosListModel
import retrofit2.http.GET

/**
 * A public interface that exposes the [getQuickVideos] method
 */
interface QuicksVideoApiService {
    /**
     * Returns a QuickVideosListModel and this method can be called from a Coroutine.
     * The @GET annotation indicates that the endpoint will be requested with the GET
     * HTTP method
     */
    @GET("search?query=nature&per_page=10&orientation=portrait")
    suspend fun getQuickVideos(): QuickVideosListModel
}