package com.kth.composeplayground.lazycolumn

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LazyColumnViewModel : ViewModel() {

    private var nextId = 3

    private val _itemList = MutableStateFlow(
        mutableStateListOf<ItemData>().apply {
            addAll((1..2).map { ItemData(it, "Item $it") })
        }
    )
    val itemList: StateFlow<List<ItemData>> = _itemList

    fun addItem() {
        val newItem = ItemData(nextId, "New Item $nextId")
        _itemList.value.add(0, newItem)
        nextId++
    }

    fun removeItem() {
        if (_itemList.value.isNotEmpty()) {
            _itemList.value.removeAt(0)
        }
    }
}