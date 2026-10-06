package com.example.ui.SeeAllScreen.SeeAllAlbum

import com.example.api.response.AlbumResponseDO
import com.example.api.response.TrackResponseDO
import com.example.core_ui.pagination.PagingState

object SeeAllAlbumContract {
    sealed interface Effect{
            data class ShowMessage (val message : String) : Effect
        }

        sealed interface Intent {
            data object LoadNextPage : Intent
            data class OnClickAlbum(val albumId : String) : Intent
        }
        data class State(
            val pagingState: PagingState<AlbumResponseDO> = PagingState(),
        )


}