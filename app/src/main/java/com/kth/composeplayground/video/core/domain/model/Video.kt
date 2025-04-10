package com.kth.composeplayground.video.core.domain.model


data class Video(
    val id: String,
    val totalDuration: Int,
    val watchedDuration: Int,
    val imgUrl: String,
    val lastWatchedTime: Long? = null
)