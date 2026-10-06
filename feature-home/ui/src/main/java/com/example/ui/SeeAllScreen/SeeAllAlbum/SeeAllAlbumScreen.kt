package com.example.ui.SeeAllScreen.SeeAllAlbum

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.core_ui.components.AlbumItem
import com.example.core_ui.pagination.Paging

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SeeAllAlbumScreen(
    state: SeeAllAlbumContract.State,
    onIntent : (SeeAllAlbumContract.Intent) -> Unit,
    onNavigateTrackList : (String) -> Unit
) {
    val gridState = rememberLazyGridState()
    Paging(
        gridState = gridState,
        preFetchOffset = 2,
        isLinearList = false,
        onFetch = { onIntent(SeeAllAlbumContract.Intent.LoadNextPage) }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0B14))
    ) {
        if (state.pagingState.isLoading && state.pagingState.dataList.isEmpty()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        } else {
            LazyVerticalGrid(
               state = gridState,
                columns = GridCells.Adaptive(minSize = 150.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = state.pagingState.dataList,
                    key = { it.id }
                ) { album ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        AlbumItem(
                            albumId = album.id,
                            imageUrl = album.image,
                            albumTitle = album.name,
                            artistTitle = album.artistName,
                            time = album.releaseDate.year.toString(),
                            onItemClick = {onNavigateTrackList(it) }
                        )
                    }
                }

                if (state.pagingState.isLoadingForNextPage) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}