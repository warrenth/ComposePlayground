package com.kth.composeplayground.video.feature.detail

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.kth.composeplayground.video.feature.home.VideoUiItem

@Stable
sealed interface VideoDetailUiState {
    @Immutable
    data object Loading : VideoDetailUiState
    @Immutable
    data class Success(val video: VideoUiItem) : VideoDetailUiState
    @Immutable
    data object Empty : VideoDetailUiState
}