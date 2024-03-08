package com.layon.quicksvideointeractionandroidapp

import android.app.Application
import com.layon.quicksvideointeractionandroidapp.data.AppContainer
import com.layon.quicksvideointeractionandroidapp.data.DefaultAppContainer

class QuicksVideoInteractionAndroidApp : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}