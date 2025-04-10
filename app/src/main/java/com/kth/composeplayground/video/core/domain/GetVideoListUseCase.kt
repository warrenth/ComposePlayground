package com.kth.composeplayground.video.core.domain

import android.util.Log
import com.kth.composeplayground.video.core.domain.model.Video
import com.kth.composeplayground.video.core.api.VideoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVideoListUseCase @Inject constructor(
    private val repository: VideoRepository
) {
    operator fun invoke(): Flow<List<Video>> {
        Log.d("VIDEO_TIME", "GetVideoListUseCase - Invoked")
        return repository.getCurrentVideoList()
    }
}