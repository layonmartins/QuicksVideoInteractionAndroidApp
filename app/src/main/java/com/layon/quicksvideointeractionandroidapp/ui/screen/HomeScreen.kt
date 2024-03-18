package com.layon.quicksvideointeractionandroidapp.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.layon.quicksvideointeractionandroidapp.ui.util.textfield.ExpandableInputSearchField
import com.layon.quicksvideointeractionandroidapp.ui.util.viewpager.EndlessVerticalViewPager

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        EndlessVerticalViewPager()  // Test QuickVideos Swipe up events
        Box(modifier = Modifier.align(Alignment.TopCenter)) {
            ExpandableInputSearchField()
        }
    }
}