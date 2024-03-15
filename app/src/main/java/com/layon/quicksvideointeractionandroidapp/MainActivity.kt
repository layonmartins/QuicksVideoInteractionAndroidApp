package com.layon.quicksvideointeractionandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.layon.quicksvideointeractionandroidapp.ui.QuicksVideoApp
import com.layon.quicksvideointeractionandroidapp.ui.screen.EndlessVerticalViewPager
import com.layon.quicksvideointeractionandroidapp.ui.screen.ShowVideoPlayer
import com.layon.quicksvideointeractionandroidapp.ui.theme.QuicksVideoInteractionAndroidAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuicksVideoInteractionAndroidAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Test QuickVideos Swipe up events
                    EndlessVerticalViewPager()
                }
            }
        }
    }
}