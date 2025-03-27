package com.kth.composeplayground.lazycolumn

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun LazyColumnScreen(
    viewModel: LazyColumnViewModel = viewModel()
) {
    val itemList by viewModel.itemList.collectAsState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                viewModel.addItem()
                scope.launch {
                    listState.animateScrollToItem(0)
                }
            }) {
                Text("Add Item")
            }

            Button(
                onClick = { viewModel.removeItem() },
                enabled = itemList.isNotEmpty()
            ) {
                Text("Remove Item")
            }
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            itemsIndexed(
                items = itemList,
                key = { index, item -> item.id }
            ) { index, item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = item.title,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
