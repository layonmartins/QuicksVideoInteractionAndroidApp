package com.layon.quicksvideointeractionandroidapp.network

import com.layon.quicksvideointeractionandroidapp.model.QuickVideosListModel
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * A public interface that exposes the [getPopularQuickVideos] method
 */
interface QuicksVideoApiService {
    /**
     * Returns a QuickVideosListModel and this method can be called from a Coroutine.
     * The @GET annotation indicates that the endpoint will be requested with the GET
     * HTTP method
     */
    @GET("popular")
    suspend fun getPopularQuickVideos(
        @Query("per_page") perPage: Int = 10,
        @Query("orientation") orientation: String = "portrait",
        @Query("max_duration") maxDuration: Int = 3600
    ): QuickVideosListModel
}