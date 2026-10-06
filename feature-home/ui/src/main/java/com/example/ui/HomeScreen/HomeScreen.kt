package com.example.ui.HomeScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.api.util.Tag
import com.example.core_data.util.ButtonState
import com.example.core_ui.R
import com.example.core_ui.components.AlbumItem
import com.example.core_ui.components.CategoryChip
import com.example.core_ui.components.EmptySectionView
import com.example.core_ui.components.SectionLayout
import com.example.core_ui.components.SectionLoadingView
import com.example.core_ui.components.TrackItem
import com.example.navigation.AppRoutes
import com.example.navigation.DeeplinkNavigator
import com.example.ui.components.ArtistCover

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    state: HomeContract.State,
    onIntent: (HomeContract.Intent) -> Unit) {
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
            .background(backgroundGradient)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tune into your world...",
                        color = Color(0xFFCDB8E6),
                        fontSize = 18.sp,
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(top = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {

                stickyHeader {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(
                            items = Tag.entries.toList(),
                            key = { it.name }
                        ) { tag ->
                            CategoryChip(
                                text = tag.name,
                                isSelected = state.selectedTab == tag,
                                onClick = {
                                    onIntent(HomeContract.Intent.OnClickTab(tag))
                                }
                            )
                        }
                    }
                }

                item {
                    SectionLayout(
                        title = "Popular Albums",
                        onSeeAllClick = { onIntent(HomeContract.Intent.OnSeeALlClick(AppRoutes.SeeAllAlbum(tag = state.selectedTab.value)))}
                    ) {
                        when {
                            state.isAlbumLoading -> {
                                SectionLoadingView()
                            }
                            state.albumsList.isEmpty() -> {
                                EmptySectionView(message = "No albums found")
                            }
                            else -> {
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    contentPadding = PaddingValues(horizontal = 16.dp)
                                ) {
                                    items(
                                        items = state.albumsList,
                                        key = { it.id }
                                    ) { album ->
                                        AlbumItem(
                                            albumId = album.id,
                                            imageUrl = album.image,
                                            albumTitle = album.name,
                                            artistTitle = album.artistName,
                                            time = album.releaseDate.year.toString(),
                                            onItemClick = {
                                                onIntent(HomeContract.Intent.OnSeeALlClick(AppRoutes.SeeAllTrack(tag = state.selectedTab.value, albumId = it)))
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    SectionLayout(
                        title = "Popular Songs",
                        onSeeAllClick = {
                            onIntent(HomeContract.Intent.OnSeeALlClick(AppRoutes.SeeAllTrack(tag = state.selectedTab.value, albumId = null)))
                        }

                    ) {
                        when {
                            state.isTrackLoading -> {
                                SectionLoadingView()
                            }
                            state.trackList.isEmpty() -> {
                                EmptySectionView(message = "No songs found")
                            }
                            else -> {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    state.trackList.take(6).forEach { track ->
                                        val btnState = if(track.id == state.chosenSong) state.playPauseBtnState
                                        else ButtonState.PLAY
                                        TrackItem(
                                            trackId = track.id,
                                            imageUrl = track.image,
                                            btnState = btnState,
                                            trackName = track.name,
                                            artistName = track.artistName,
                                            onIconClick = {
                                                onIntent(HomeContract.Intent.OnPausePlayIconClick(it))
                                            },
                                            isChosen = state.chosenSong == track.id,
                                            onTrackClick = {
                                                onIntent(
                                                    HomeContract.Intent.OnItemClick(
                                                        trackId = track.id,
                                                        deeplinkNavigator =
                                                            DeeplinkNavigator.DetailMusic(argument = track.id)
                                                        )

                                                )
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    SectionLayout(
                        title = "Popular Artists",
                        onSeeAllClick = {}

                    ) {
                        when {
                            state.isArtistLoading -> {
                                SectionLoadingView()
                            }
                            state.artistList.isEmpty() -> {
                                EmptySectionView(message = "No artists found")
                            }
                            else -> {
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    contentPadding = PaddingValues(horizontal = 16.dp)
                                ) {
                                    items(
                                        items = state.artistList,
                                        key = { it.id }
                                    ) { artist ->
                                        ArtistCover(
                                            artist = artist,
                                            onArtistClick = {}
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}