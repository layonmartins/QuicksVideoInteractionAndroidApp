package com.layon.quicksvideointeractionandroidapp.ui.util.video

import android.annotation.SuppressLint
import android.net.Uri
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.layon.quicksvideointeractionandroidapp.R
import java.io.File


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShowVideoPlayer(videoUri: Uri) {
    Scaffold(
        content = {
            VideoPlayer(videoUri)
        }
    )
}

@SuppressLint("OpaqueUnitKey")
@Composable
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun VideoPlayer(uri: Uri) {
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                val defaultDataSourceFactory = DefaultDataSource.Factory(context)
                val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
                    context,
                    defaultDataSourceFactory
                )
                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(uri))

                setMediaSource(source)
                prepare()
            }
    }

    exoPlayer.playWhenReady = true
    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    exoPlayer.repeatMode = Player.REPEAT_MODE_ONE

    DisposableEffect(
        AndroidView(factory = {
            PlayerView(context).apply {
                hideController()
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM

                player = exoPlayer
                layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            }
        })
    ) {
        onDispose { exoPlayer.release() }
    }

    PlayerControls(
        playVideo = {
            exoPlayer.play()
        },
        pauseVideo = {
            exoPlayer.pause()
        }
    )

    interactionsVideoLayout()
}


@Composable
fun PlayerControls(
    modifier: Modifier = Modifier,
    pauseVideo: () -> Unit,
    playVideo: () -> Unit
) {
    var videoIsPlaying by remember { mutableStateOf(true) }
    Column(
        modifier = modifier.fillMaxSize().clickable {
            if (videoIsPlaying) { pauseVideo() } else { playVideo() }
            videoIsPlaying = !videoIsPlaying
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        //pause/play toggle button
            Image(
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Crop,
                alpha = if (videoIsPlaying) 0.0F else 0.8F,
                painter = painterResource(id = R.drawable.baseline_play_circle_outline_24),
                contentDescription = "Play/Pause"
            )
    }
}


