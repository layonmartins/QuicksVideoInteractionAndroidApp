package com.layon.quicksvideointeractionandroidapp.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.layon.quicksvideointeractionandroidapp.QuicksVideoInteractionAndroidApp
import com.layon.quicksvideointeractionandroidapp.data.QuicksVideoRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Quick Video screen
 */
sealed interface QuickVideoUiState {
    data class Success(val quickVideos: String) : QuickVideoUiState
    object Error : QuickVideoUiState
    object Loading : QuickVideoUiState
}

class QuickVideosViewModel(private val quicksVideoRepository: QuicksVideoRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var quickVideoUiState: QuickVideoUiState by mutableStateOf(QuickVideoUiState.Loading)
        private set

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getQuickVideo()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [MutableList].
     */
    fun getQuickVideo() {
        viewModelScope.launch {
            quickVideoUiState = QuickVideoUiState.Loading
            quickVideoUiState = try {
                val listResult = quicksVideoRepository.getQuickVideos().videos
                QuickVideoUiState.Success(
                    "Success: ${listResult.size} quick videos retrieved"
                )
            } catch (e: IOException) {
                QuickVideoUiState.Error
            } catch (e: HttpException) {
                QuickVideoUiState.Error
            }
        }
    }

    /**
     * Factory for [MarsViewModel] that takes [MarsPhotosRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as QuicksVideoInteractionAndroidApp)
                val quicksVideoRepository = application.container.quicksVideoRepository
                QuickVideosViewModel(quicksVideoRepository = quicksVideoRepository)
            }
        }
    }
}