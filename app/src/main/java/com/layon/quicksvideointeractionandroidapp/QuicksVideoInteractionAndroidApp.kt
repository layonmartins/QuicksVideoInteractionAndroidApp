package com.layon.quicksvideointeractionandroidapp

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.layon.quicksvideointeractionandroidapp.data.AppContainer
import com.layon.quicksvideointeractionandroidapp.data.DefaultAppContainer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class QuicksVideoInteractionAndroidApp : Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: HiltWorkerFactory

    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setMinimumLoggingLevel(Log.VERBOSE)
            .setWorkerFactory(workerFactory)
            .build()

  override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}