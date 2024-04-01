package com.layon.quicksvideointeractionandroidapp.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.layon.quicksvideointeractionandroidapp.data.baseUrl
import com.layon.quicksvideointeractionandroidapp.data.pexelApiKey
import com.layon.quicksvideointeractionandroidapp.network.QuicksVideoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val authInterceptor = Interceptor() { chain ->
            val request =
                chain.request().newBuilder().addHeader("Authorization", pexelApiKey).build()
            chain.proceed(request)
        }
        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(logInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        val jsonProperties = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(jsonProperties.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Singleton
    @Provides
    fun provideQuicksVideoApiService(retrofit: Retrofit): QuicksVideoApiService {
        return retrofit.create(QuicksVideoApiService::class.java)
    }
}