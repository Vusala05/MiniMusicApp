package com.example.core_ui.pagination

import com.example.core_data.model.AppError
import com.example.core_data.model.ResultWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


 class PaginationHandler<T>(
     val coroutineScope : CoroutineScope,
     val limit: Int = 15,
     val onGetData : suspend (Int) -> ResultWrapper<List<T>>,
     val onError : suspend (AppError) -> Unit,
     val onLoadedNewDataList : suspend (List<T>) -> Unit = {}
 ){

     private val _pagingState = MutableStateFlow(PagingState<T>())
     val pagingState  = _pagingState.asStateFlow()
     private var job: Job? = null

     fun loadInitialData() = loadData(reset = true)
     fun loadNextPage() = loadData(reset = false)

    private  fun loadData(reset: Boolean) {
        val state = _pagingState.value
        if (!reset && (state.paginationReachedEnd || state.isLoading || state.isLoadingForNextPage)) return

        job?.cancel()
        val offset = if (reset) 0 else state.offSet
          _pagingState.update { it.copy(isLoading = reset, isLoadingForNextPage = !reset) }

        job = coroutineScope.launch {
            when (val res = onGetData(offset)) {
                is ResultWrapper.Success -> {
                    _pagingState.update {
                        it.copy(
                            dataList = if (reset) res.data else it.dataList + res.data,
                            offSet = offset + res.data.size,
                            paginationReachedEnd = res.data.size < limit,
                            isLoading = false,
                            isLoadingForNextPage = false
                        )
                    }
                    if(!reset) onLoadedNewDataList(res.data)

                }
                is ResultWrapper.Error -> {
                    _pagingState.update { it.copy(isLoading = false, isLoadingForNextPage = false) }
                    onError(res.appError)
                }
            }
        }
    }
}