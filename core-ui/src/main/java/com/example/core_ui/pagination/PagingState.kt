package com.example.core_ui.pagination

data class PagingState<T>(
    val dataList : List<T> = emptyList(),
    val isLoading : Boolean = false,
    val isLoadingForNextPage : Boolean = false,
    val paginationReachedEnd : Boolean = false,
    val offSet: Int = 0
) {
}