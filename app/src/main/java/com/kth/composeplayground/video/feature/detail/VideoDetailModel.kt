package com.kth.composeplayground.video.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kth.composeplayground.video.core.domain.GetVideoListUseCase
import com.kth.composeplayground.video.core.domain.UpdateWatchedDurationUseCase
import com.kth.composeplayground.video.feature.home.toUiItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val updateWatchedDurationUseCase: UpdateWatchedDurationUseCase,
    private val getVideoListUseCase: GetVideoListUseCase
) : ViewModel() {

    private val videoId: String = checkNotNull(savedStateHandle["videoId"])

    val uiState: StateFlow<VideoDetailUiState> = getVideoListUseCase()
        .map { list ->
            val item = list.firstOrNull { it.id == videoId }?.toUiItem()
            when (item) {
                null -> VideoDetailUiState.Empty
                else -> VideoDetailUiState.Success(item)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), VideoDetailUiState.Loading)

    fun updateWatchedDuration(duration: Int) {
        viewModelScope.launch {
            updateWatchedDurationUseCase(videoId, duration)
        }
    }
}