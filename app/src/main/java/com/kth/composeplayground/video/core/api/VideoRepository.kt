package com.kth.composeplayground.video.core.api

import com.kth.composeplayground.video.core.domain.model.Video
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * core:data-api
 */
interface VideoRepository {
    fun getCurrentVideoList(): Flow<List<Video>>
    suspend fun deleteVideo(id: String)
    suspend fun updateVideoList(videos: List<Video>)
}