package com.kth.composeplayground.video.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kth.composeplayground.video.core.domain.GetVideoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val getVideos: GetVideoListUseCase
) : ViewModel() {

    val uiState: StateFlow<VideoUiState> = getVideos()
        .map {
            Log.d("VIDEO_TIME", "list() - Received from Flow (first item watchedDuration): " + it.firstOrNull()?.watchedDuration)
            VideoUiState.Success(
                it.map { video -> video.toUiItem() }.toPersistentList()
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(500), VideoUiState.Loading)

    init {
        Log.d("VIDEO_TIME", "VideoListViewModel - Initialized")
    }}

