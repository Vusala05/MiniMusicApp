package com.example.ui.SeeAllScreen.SeeAllTrack

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_data.util.ButtonState
import com.example.core_ui.R
import com.example.core_ui.components.EmptySectionView
import com.example.core_ui.components.SectionLoadingView
import com.example.core_ui.components.TrackItem
import com.example.core_ui.pagination.Paging
import com.example.navigation.DeeplinkNavigator

@Composable
fun SeeAllScreen(
    state: SeeAllTrackContract.State,
    onIntent: (SeeAllTrackContract.Intent) -> Unit
) {
    val listState = rememberLazyListState()

    Paging(
        enabled = true,
        listState = listState,
        preFetchOffset = 3,
        onFetch = { onIntent(SeeAllTrackContract.Intent.LoadNextPage) },
        isLinearList = true
    )

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF1E132A),
            Color(0xFF0F0B18),
            Color(0xFF0D0B14)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(backgroundGradient)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0),
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Popular Songs",
                        color = Color(0xFFCDB8E6),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(R.drawable.music_icon),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        ) { innerPadding ->
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(top = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                when {
                    state.pagingState.isLoading -> {
                        item { SectionLoadingView() }
                    }
                    state.pagingState.dataList.isEmpty() -> {
                        item { EmptySectionView(message = "No songs found") }
                    }
                    else -> {
                        items(
                            items = state.pagingState.dataList,
                            key = { it.id }
                        ) { track ->
                            val btnState = if (track.id == state.chosenSong) {
                                state.playPauseBtnState
                            } else {
                                ButtonState.PLAY
                            }

                            TrackItem(
                                trackId = track.id,
                                imageUrl = track.image,
                                btnState = btnState,
                                trackName = track.name,
                                artistName = track.artistName,
                                isChosen = state.chosenSong == track.id,
                                onIconClick = {
                                    onIntent(SeeAllTrackContract.Intent.OnPlayPauseIconClick(it))
                                },
                                onTrackClick = {
                                    onIntent(
                                        SeeAllTrackContract.Intent.OnClickTrackItem(
                                            trackId = track.id,
                                            deeplinkNavigator =
                                                DeeplinkNavigator.DetailMusic(argument = track.id)))

                                }
                            )
                        }

                        if (state.pagingState.isLoadingForNextPage) {
                            item { SectionLoadingView() }
                        }
                    }
                }
            }
        }
    }
}