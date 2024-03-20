package com.layon.quicksvideointeractionandroidapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.layon.quicksvideointeractionandroidapp.R
import com.layon.quicksvideointeractionandroidapp.ui.screen.viewmodel.HomeScreenViewModel
import com.layon.quicksvideointeractionandroidapp.ui.screen.viewmodel.QuickVideoUiState
import com.layon.quicksvideointeractionandroidapp.ui.util.textfield.ExpandableInputSearchField
import com.layon.quicksvideointeractionandroidapp.ui.util.video.interactionsVideoLayout
import com.layon.quicksvideointeractionandroidapp.ui.util.viewpager.EndlessVerticalViewPager

@Composable
fun HomeScreen() {

    //get viewModel reference
    val homeScreenViewModel: HomeScreenViewModel =
        viewModel(factory = HomeScreenViewModel.Factory)

    // Mocked videos list to test
    var quickVideosPathList = mutableListOf(
        "/data/data/com.layon.quicksvideointeractionandroidapp/files/quickvideos/video1.mp4",
        "/data/data/com.layon.quicksvideointeractionandroidapp/files/quickvideos/video2.mp4",
        "/data/data/com.layon.quicksvideointeractionandroidapp/files/quickvideos/video3.mp4"
    )

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        when (homeScreenViewModel.quickVideoUiState) {
            is QuickVideoUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
            is QuickVideoUiState.Error -> ErrorScreen(modifier = Modifier.fillMaxSize())
            is QuickVideoUiState.Success -> {
                quickVideosPathList.add((homeScreenViewModel.quickVideoUiState as QuickVideoUiState.Success).quickVideoPath)
                EndlessVerticalViewPager(quickVideosPathList)
            }
        }
        Box(modifier = Modifier.align(Alignment.TopCenter)) {
            ExpandableInputSearchField()
        }
    }

}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = "Error to load Home Screen", modifier = Modifier.padding(16.dp))
    }
}