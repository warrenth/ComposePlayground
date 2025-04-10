package com.kth.composeplayground.video.core.domain

import com.kth.composeplayground.video.core.api.VideoRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateWatchedDurationUseCase @Inject constructor(
    private val repository: VideoRepository
) {
    suspend operator fun invoke(id: String, duration: Int) {
        // 1. 저장된 비디오 리스트 가져온다.
        val updatedList = repository.getCurrentVideoList()
            .first()

        // 2. 수정할 항목을 찾아 업데이트
        val updated = updatedList.map { video ->
            if (video.id == id) {
                video.copy(
                    watchedDuration = duration,
                    lastWatchedTime = System.currentTimeMillis()
                )
            } else {
                video
            }
        }

        // 3. 수정된 데이터를 repository에 반영
        repository.updateVideoList(updated)
    }
}