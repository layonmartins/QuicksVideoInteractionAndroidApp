package com.layon.quicksvideointeractionandroidapp.ui.util.viewpager

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.layon.quicksvideointeractionandroidapp.ui.screen.ErrorScreen
import com.layon.quicksvideointeractionandroidapp.ui.screen.LoadingScreen
import com.layon.quicksvideointeractionandroidapp.ui.screen.viewmodel.QuickVideoUiState
import com.layon.quicksvideointeractionandroidapp.ui.util.video.ShowVideoPlayer

/**
 * This composable function create an event of
 * swipe up the videos
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EndlessVerticalViewPager(
    quickVideosUiStateList : List<QuickVideoUiState>,
    updateQuickVideos: ( Int ) -> Unit =  {}
    ) {
    val pageCount = Int.MAX_VALUE // https://stackoverflow.com/questions/75468555/how-to-create-an-endless-pager-in-jetpack-compose
    val realSize = quickVideosUiStateList.size
    var coveredCount by remember { mutableStateOf(0) }
    val videosToFetch = 1 // Numbers of quick videos that be fetch from API at time
    val pagerState = rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0f) { pageCount }

    // Side effect when the user swipe the page
    LaunchedEffect(key1 = pagerState.currentPage) {
        if (coveredCount == videosToFetch) {
            //TODO call a function in this coroutines to request news animals
            Log.d("layonf", "EndlessVerticalViewPager call updateQuickVideos pagerState.currentPage: ${pagerState.currentPage}")
            updateQuickVideos(pagerState.currentPage)
            coveredCount = 0
        }
        coveredCount++
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        VerticalPager(state = pagerState,) { index ->
            val realIndex = index % realSize
            when (quickVideosUiStateList[realIndex]) {
                is QuickVideoUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
                is QuickVideoUiState.Error -> Text("Error to show this video")
                is QuickVideoUiState.Success -> {
                    val videoUri = (quickVideosUiStateList[realIndex] as QuickVideoUiState.Success).quickVideoUri
                    if (videoUri != null) {
                        ShowVideoPlayer(videoUri)
                    }
                }
            }

        }
    }

}