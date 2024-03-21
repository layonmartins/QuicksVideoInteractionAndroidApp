package com.layon.quicksvideointeractionandroidapp.network

import com.layon.quicksvideointeractionandroidapp.model.QuickVideosListModel
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * A public interface that exposes the [getSearchedQuickVideos] method
 */
interface QuicksVideoApiService {
    /**
     * Returns a QuickVideosListModel and this method can be called from a Coroutine.
     * The @GET annotation indicates that the endpoint will be requested with the GET
     * HTTP method
     */
    @GET("search")
    suspend fun getSearchedQuickVideos(
        @Query("query") query: String = "",
        @Query("per_page") perPage: Int = 1,
        @Query("page") page: Int = 1,
        @Query("orientation") orientation: String = "portrait",
        @Query("size") size: String = "medium"
    ): QuickVideosListModel
}