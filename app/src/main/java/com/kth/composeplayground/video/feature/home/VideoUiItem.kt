package com.kth.composeplayground.video.feature.home

import androidx.compose.runtime.Immutable
import com.kth.composeplayground.video.core.domain.model.Video

@Immutable
data class VideoUiItem(
    val id: String,
    val totalDuration: Int,
    val watchedDuration: Int,
    val imgUrl: String,
    val lastWatchedTime: Long? = null
) {
    val stableKey: String
        get() = "$id-${lastWatchedTime ?: 0L}"
}

fun Video.toUiItem(): VideoUiItem = VideoUiItem(
    id = id,
    totalDuration = totalDuration,
    watchedDuration = watchedDuration,
    imgUrl = imgUrl,
    lastWatchedTime = lastWatchedTime
)