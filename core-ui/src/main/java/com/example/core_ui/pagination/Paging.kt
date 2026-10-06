
package com.example.core_ui.pagination

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember


@Composable
fun Paging(
    enabled: Boolean = true,
    listState: LazyListState?=null,
    gridState: LazyGridState?=null,
    preFetchOffset: Int = 0,
    onFetch: () -> Unit,
    isLinearList : Boolean
) {
    if (!enabled) return


    val lastVisibleItemIndex by remember {
         derivedStateOf { if(isLinearList) listState?.layoutInfo?.visibleItemsInfo?.lastOrNull()?.index
         else gridState?.layoutInfo?.visibleItemsInfo?.lastOrNull()?.index}

    }
    val totalItemsCount by remember {
        derivedStateOf {
            if(isLinearList)  listState?.layoutInfo?.totalItemsCount ?:0
            else gridState?.layoutInfo?.totalItemsCount ?: 0
        }
    }

    LaunchedEffect(enabled, lastVisibleItemIndex) {
        val lastVisibleItemIndex = lastVisibleItemIndex
        if (lastVisibleItemIndex == null || lastVisibleItemIndex == 0) return@LaunchedEffect

        if ((lastVisibleItemIndex) >= totalItemsCount - 1 - preFetchOffset) {
            onFetch()
        }
    }
}
