package com.example.ui.SeeAllScreen.SeeAllAlbum

import android.widget.MediaController
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.api.useCases.GetAlbumsUseCase
import com.example.core_data.useCases.HandleErrorUseCase
import com.example.core_ui.model.BaseViewModel
import com.example.core_ui.pagination.PaginationHandler
import com.example.service.MusicController
import com.example.ui.SeeAllScreen.SeeAllTrack.SeeAllContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeAllAlbumViewModel @Inject constructor(
    val getAlbumsUseCase: GetAlbumsUseCase,
    val handleErrorUseCase: HandleErrorUseCase,
    savedStateHandle: SavedStateHandle
): BaseViewModel<SeeAllAlbumContract.State, SeeAllAlbumContract.Effect>(
    initialState = SeeAllAlbumContract.State()
) {
    private val tag: String? = savedStateHandle["type"]

    val paginationHandler = PaginationHandler(
        coroutineScope = viewModelScope,
        onGetData = { offset -> getAlbumsUseCase(offset = offset, tag = tag) },
        onError = { appError -> sendEffect(SeeAllAlbumContract.Effect.ShowMessage(handleErrorUseCase(appError))) },
    )

    init {
        collectPaginationState()
        viewModelScope.launch{
            paginationHandler.loadInitialData()
        }
    }


    fun handleIntent(intent: SeeAllAlbumContract.Intent) {
        when(intent){
            is SeeAllAlbumContract.Intent.OnClickAlbum -> {

            }
            is SeeAllAlbumContract.Intent.LoadNextPage -> {
                viewModelScope.launch {
                    paginationHandler.loadNextPage()
                }

            }

        }
    }


    private fun collectPaginationState(){
        viewModelScope.launch {
            paginationHandler.pagingState.collect { paginationState ->
                updateUiState { it.copy(pagingState = paginationState) }
            }
        }

    }


}