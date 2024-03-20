package com.layon.quicksvideointeractionandroidapp.ui.screen.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.layon.quicksvideointeractionandroidapp.QuicksVideoInteractionAndroidApp
import com.layon.quicksvideointeractionandroidapp.data.QuicksVideoRepository
import com.layon.quicksvideointeractionandroidapp.model.VideoFiles
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeScreenViewModel(private val quicksVideoRepository: QuicksVideoRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var quickVideoUiState: QuickVideoUiState by mutableStateOf(QuickVideoUiState.Loading)
        private set

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getQuickVideoFromApi()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [MutableList].
     */
    private fun getQuickVideoFromApi() {
        viewModelScope.launch {
            quickVideoUiState = QuickVideoUiState.Loading
            quickVideoUiState = try {
                val videoPath = getBestVideoFileQualityLink(quicksVideoRepository.getSearchedQuickVideos().videos.first().videoFiles)
                Log.d("layonf", "listResult: ${videoPath}")
                QuickVideoUiState.Success(
                    quickVideoPath = videoPath,
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
        }
    }

    //TODO
    private fun saveVideoInInternalStorage(url: String) : String {
        return "videoPath"
    }

    /**
     * Factory for [HomeScreenViewModel]
     */
    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as QuicksVideoInteractionAndroidApp)
                val quicksVideoRepository = application.container.quicksVideoRepository
                HomeScreenViewModel(quicksVideoRepository = quicksVideoRepository)
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
        val quickVideoPath: String,
        val likes: Int,
        val shared: Int,
        val musicAudioPath: String,
        val comments: List<String>) : QuickVideoUiState
    object Error : QuickVideoUiState
    object Loading : QuickVideoUiState
}