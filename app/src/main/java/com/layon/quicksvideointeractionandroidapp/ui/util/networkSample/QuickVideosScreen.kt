package com.layon.quicksvideointeractionandroidapp.ui.util.networkSample

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.layon.quicksvideointeractionandroidapp.R
import com.layon.quicksvideointeractionandroidapp.ui.theme.QuicksVideoInteractionAndroidAppTheme

@Composable
fun QuickVideosScreen(
    quickVideoUiState: QuickVideoUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)) {
    when (quickVideoUiState) {
        is QuickVideoUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is QuickVideoUiState.Success -> ResultScreen(
            quickVideoUiState.quickVideos, modifier = modifier.fillMaxWidth()
        )
        is QuickVideoUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
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
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}

/**
 * ResultScreen displaying number of photos retrieved.
 */
@Composable
fun ResultScreen(photos: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = photos)
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    QuicksVideoInteractionAndroidAppTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    QuicksVideoInteractionAndroidAppTheme {
        ErrorScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun PhotosGridScreenPreview() {
    QuicksVideoInteractionAndroidAppTheme {
        ResultScreen(stringResource(R.string.placeholder_success))
    }
}