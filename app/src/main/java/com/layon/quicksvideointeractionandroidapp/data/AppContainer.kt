package com.layon.quicksvideointeractionandroidapp.data

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.layon.quicksvideointeractionandroidapp.network.QuicksVideoApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

const val baseUrl = "https://api.pexels.com/videos/"
const val pexelApiKey = "l2djyprABkcfC3TyvMLwXAxy7S1TnlLVyT79AGEc2iXTwXhD6kkFYlYg" // see how to create you pexel api -> https://www.pexels.com/api/new/

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val quickVideosWorkManagerRepository: QuicksVideoRepository
}

class DefaultAppContainer(context: Context) : AppContainer {

    override val quickVideosWorkManagerRepository: QuicksVideoRepository by lazy {
        QuickVideosWorkManagerRepository(context)
    }
}