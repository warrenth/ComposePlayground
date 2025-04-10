package com.kth.composeplayground.video.feature.home

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.kth.composeplayground.video.feature.detail.VideoDetailUiState
import kotlinx.collections.immutable.ImmutableList

@Stable
sealed interface VideoUiState {
    @Immutable
    data object Loading : VideoUiState
    @Immutable
    data class Success(val videos: ImmutableList<VideoUiItem>) : VideoUiState
    @Immutable
    data object Empty : VideoUiState
}