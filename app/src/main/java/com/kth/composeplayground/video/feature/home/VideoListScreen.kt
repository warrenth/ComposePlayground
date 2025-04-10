package com.kth.composeplayground.video.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.kth.composeplayground.video.core.ui.EmptyView
import com.kth.composeplayground.video.core.ui.LoadingView

@Composable
fun VideoListScreen(
    onVideoClick: (VideoUiItem) -> Unit = {}
) {
    val viewModel: VideoListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is VideoUiState.Loading -> LoadingView()
        is VideoUiState.Empty -> EmptyView()
        is VideoUiState.Success -> {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {

                Text(
                    text = "My Watch History",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(10.dp))

                VerticalVideoList(state.videos, onVideoClick)
            }
        }
    }
}

@Composable
private fun VerticalVideoList(
    videos: List<VideoUiItem>,
    onVideoClick: (VideoUiItem) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(end = 8.dp)
    ) {
        items(videos,  key = { it.stableKey }) { item ->
            Column(
                modifier = Modifier
                    .width(120.dp)
                    .padding(end = 8.dp)
                    .clickable { onVideoClick(item) },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = item.imgUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .aspectRatio(16f / 9f)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "${item.watchedDuration}s / ${item.totalDuration}s",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 5.dp)
                )
            }
        }
    }
}















