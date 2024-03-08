package com.layon.quicksvideointeractionandroidapp.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.layon.quicksvideointeractionandroidapp.network.QuicksVideoApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val quicksVideoRepository: QuicksVideoRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer : AppContainer {

    /**
     * See API documentation:
     * @link: https://www.pexels.com/api/documentation/#authorization
     */

    private val baseUrl = "https://api.pexels.com/videos/"
    private val pexelApiKey = "add_your_api_here" // see how to create you pexel api -> https://www.pexels.com/api/new/
    private val jsonProperties = Json { ignoreUnknownKeys = true }

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(jsonProperties.asConverterFactory("application/json".toMediaType()))
        .client(OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request().newBuilder().addHeader("Authorization", pexelApiKey).build()
            chain.proceed(request)
        }.build())
        .baseUrl(baseUrl)
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: QuicksVideoApiService by lazy {
        retrofit.create(QuicksVideoApiService::class.java)
    }

    /**
     * DI implementation for Mars photos repository
     */
    override val quicksVideoRepository: QuicksVideoRepository by lazy {
        NetworkQuicksVideoRepository(retrofitService)
    }
}