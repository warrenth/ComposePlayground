package com.kth.composeplayground.video.feature.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.kth.composeplayground.video.core.ui.EmptyView
import com.kth.composeplayground.video.core.ui.LoadingView
import com.kth.composeplayground.video.feature.home.VideoUiItem

@Composable
fun VideoDetailScreen(
    viewModel: VideoDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is VideoDetailUiState.Loading -> LoadingView()
        is VideoDetailUiState.Empty -> EmptyView()
        is VideoDetailUiState.Success -> VideoDetailContent(
            video = state.video,
            onUpdate = viewModel::updateWatchedDuration
        )
    }
}


@Composable
fun VideoDetailContent(
    video: VideoUiItem,
    onUpdate: (Int) -> Unit
) {
    var watchedTime by remember { mutableStateOf(video.watchedDuration) }
    val currentOnUpdate by rememberUpdatedState(onUpdate)

    LaunchedEffect(video.id) {
        while (watchedTime < video.totalDuration) {
            kotlinx.coroutines.delay(1000)
            watchedTime++
            currentOnUpdate(watchedTime)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        item {
            AsyncImage(
                model = video.imgUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "$watchedTime 초 / ${video.totalDuration}초",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}