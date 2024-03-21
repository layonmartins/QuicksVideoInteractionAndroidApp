package com.layon.quicksvideointeractionandroidapp.ui.screen

import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.layon.quicksvideointeractionandroidapp.R
import com.layon.quicksvideointeractionandroidapp.ui.screen.viewmodel.HomeScreenViewModel
import com.layon.quicksvideointeractionandroidapp.ui.util.textfield.ExpandableInputSearchField
import com.layon.quicksvideointeractionandroidapp.ui.util.viewpager.EndlessVerticalViewPager
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {

    //get viewModel reference
    val homeScreenViewModel: HomeScreenViewModel =
        viewModel(factory = HomeScreenViewModel.Factory)

    val query = remember { mutableStateOf("car") } /// Default query = car
    val page = remember { mutableStateOf(0) }
    val isLoading = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {
        if(isLoading.value) {
            LoadingScreen(Modifier.align(Alignment.Center))
        } else {
            EndlessVerticalViewPager(
                quickVideosUiStateList = homeScreenViewModel.quickVideosUiStateList,
                updateQuickVideos = {
                    coroutineScope.launch {
                        page.value = it
                        homeScreenViewModel.getNewQuickVideoFromApi(query = query.value, page = page.value)
                    }
                })
        }

        Box(modifier = Modifier.align(Alignment.TopCenter)) {
            ExpandableInputSearchField(
                onDoneSearchTextField = {
                    coroutineScope.launch {
                        isLoading.value = true
                        query.value = it
                        //TODO clear all downloaded videos to clear the data base
                        //TODO request some videos, for exemple 3 or 5 videos
                        homeScreenViewModel.getNewQuickVideoFromApi(query = query.value, page = page.value)
                        isLoading.value = false
                    }
                }
            )
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