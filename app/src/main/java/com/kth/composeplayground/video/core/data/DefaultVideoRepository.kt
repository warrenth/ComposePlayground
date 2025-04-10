package com.kth.composeplayground.video.core.data

import android.util.Log
import com.kth.composeplayground.video.core.api.VideoRepository
import com.kth.composeplayground.video.core.domain.model.Video
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class DefaultVideoRepository @Inject constructor() : VideoRepository {

    private val mutex = Mutex()
    private var cachedVideoList: MutableList<Video> = sampleVideos.toMutableList()

    override fun getCurrentVideoList(): Flow<List<Video>> = flow {
        Log.d("VIDEO_TIME", "getCurrentVideoList() - Before Emit: " + cachedVideoList.firstOrNull()?.watchedDuration)
        emit(cachedVideoList)
        Log.d("VIDEO_TIME", "getCurrentVideoList() - After Emit: " + cachedVideoList.firstOrNull()?.watchedDuration)
    }

    override suspend fun updateVideoList(videos: List<Video>) {
        mutex.withLock {
            Log.d("VIDEO_TIME", "update() - Before Clear: " + cachedVideoList.firstOrNull()?.watchedDuration)
            Log.d("VIDEO_TIME", "update() - Received videos (first item watchedDuration): " + videos.firstOrNull()?.watchedDuration)
            cachedVideoList.clear()
            cachedVideoList.addAll(videos)
            Log.d("VIDEO_TIME", "updated() - After Update: " + cachedVideoList.firstOrNull()?.watchedDuration)
        }
    }

    override suspend fun deleteVideo(id: String) {
        mutex.withLock {
            Log.d("VIDEO_TIME", "deleteVideo() - Before Delete: " + cachedVideoList.firstOrNull()?.watchedDuration)
            cachedVideoList.removeAll { it.id == id }
            Log.d("VIDEO_TIME", "deleteVideo() - After Delete: " + cachedVideoList.firstOrNull()?.watchedDuration)
        }
    }
}

val sampleVideos = listOf(
    Video("1", 100, 0, "https://placedog.net/400/300"),
    Video("2", 30, 0, "https://placedog.net/401/300"),
    Video("3", 40, 0, "https://placedog.net/402/300"),
    Video("4", 20, 0, "https://placedog.net/403/300"),
    Video("5", 50, 0, "https://placedog.net/404/300"),
    Video("6", 70, 0, "https://placedog.net/405/300"),
    Video("7", 80, 0, "https://placedog.net/406/300"),
    Video("8", 130, 0, "https://placedog.net/407/300"),
    Video("9", 320, 0, "https://placedog.net/408/300"),
    Video("10", 130, 0, "https://placedog.net/409/300")
)