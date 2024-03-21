package com.layon.quicksvideointeractionandroidapp.ui.screen.viewmodel

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.layon.quicksvideointeractionandroidapp.QuicksVideoInteractionAndroidApp
import com.layon.quicksvideointeractionandroidapp.data.QuicksVideoRepository
import com.layon.quicksvideointeractionandroidapp.model.VideoFiles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL

class HomeScreenViewModel(
    private val context: Context, // TODO should be removed when implemented workmanger
    private val quicksVideoRepository: QuicksVideoRepository
) : ViewModel() {
    var quickVideosUiStateList = mutableStateListOf<QuickVideoUiState>()
        private set

    init {
        // Take the initial videos
        //TODO get this from DataBase
        quickVideosUiStateList.addAll(
            listOf(
                QuickVideoUiState.Success(
                    quickVideoUri = Uri.fromFile(File("/data/data/com.layon.quicksvideointeractionandroidapp/files/quickvideos/video1.mp4")),
                    likes = 1000,
                    shared = 500,
                    musicAudioPath = "MusicLink",
                    comments = listOf("asdf", "asdf", "asdf")
                ),
                QuickVideoUiState.Success(
                    quickVideoUri = Uri.fromFile(File("/data/data/com.layon.quicksvideointeractionandroidapp/files/quickvideos/video2.mp4")),
                    likes = 1000,
                    shared = 500,
                    musicAudioPath = "MusicLink",
                    comments = listOf("asdf", "asdf", "asdf")
                ),
                QuickVideoUiState.Success(
                    quickVideoUri = Uri.fromFile(File("/data/data/com.layon.quicksvideointeractionandroidapp/files/quickvideos/video3.mp4")),
                    likes = 1000,
                    shared = 500,
                    musicAudioPath = "MusicLink",
                    comments = listOf("asdf", "asdf", "asdf")
                ),
            )
        )
    }

    //TODO move this function to workManager
    suspend fun getNewQuickVideoFromApi(
        query: String,
        page: Int) {
        viewModelScope.launch {
            Log.d("layonf", "getNewQuickVideoFromApi query: $query, page: $page")
            quickVideosUiStateList.add(
                try {
                    val videos = quicksVideoRepository.getSearchedQuickVideos(query, page).videos.first()
                    val videoName = videos.url ?: videos.id.toString()
                    val videoUrl = getBestVideoFileQualityLink(videos.videoFiles)
                    val videoUri = saveVideoInInternalStorage(videoName, videoUrl)
                    Log.d("layonf", "getNewQuickVideoFromApi QuickVideoUiState.Success: $videoUri")
                    QuickVideoUiState.Success(
                        quickVideoUri = videoUri,
                        likes = 1000,
                        comments = listOf(
                            LoremIpsum().values.first().take(10),
                            LoremIpsum().values.first().take(20),
                            LoremIpsum().values.first().take(30),
                            LoremIpsum().values.first().take(40),
                        ),
                        shared = 1000,
                        musicAudioPath = ""
                    )
                } catch (e: IOException) {
                    QuickVideoUiState.Error
                } catch (e: HttpException) {
                    QuickVideoUiState.Error
                }
            )
        }
    }

    //TODO move this function to workerManager 2
    private suspend fun saveVideoInInternalStorage(videoName: String, videoUrl: String) : Uri? {

        return withContext(Dispatchers.IO) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, videoName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
                    //put(MediaStore.MediaColumns.RELATIVE_PATH, "/data/data/com.layon.quicksvideointeractionandroidapp/files/quickvideos/")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/DownloaderDemo")
                }
                val resolver = context.contentResolver
                val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

                 if (uri!=null) {
                    URL(videoUrl).openStream().use { input ->
                        resolver.openOutputStream(uri).use { output -> input.copyTo(output!!, DEFAULT_BUFFER_SIZE)
                        }
                    }
                    uri
                } else {
                    null
                }
            } else {
                val target = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    videoName
                )
                URL(videoUrl).openStream().use { input ->
                    FileOutputStream(target).use { output ->
                        input.copyTo(output)
                    }
                }
                 target.toUri()
            }
        }


    }


    /**
     * Factory for [HomeScreenViewModel]
     */
    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as QuicksVideoInteractionAndroidApp)
                val quicksVideoRepository = application.container.quicksVideoRepository
                HomeScreenViewModel(context = application, quicksVideoRepository = quicksVideoRepository)
            }
        }

        //TODO add unit test
        fun getBestVideoFileQualityLink(videoFiles: ArrayList<VideoFiles>) : String {
            var video : VideoFiles ? = videoFiles.first()
            videoFiles.forEach { next ->
                if (video?.compareTo(next) == -1 && video?.link != null) {
                    video = next
                }
            }
            return video?.link!!
        }
    }
}



/**
 * UI state for the Quick Video screen
 */
sealed interface QuickVideoUiState {
    data class Success(
        val quickVideoUri: Uri?,
        val likes: Int,
        val shared: Int,
        val musicAudioPath: String,
        val comments: List<String>) : QuickVideoUiState
    object Error : QuickVideoUiState
    object Loading : QuickVideoUiState
}