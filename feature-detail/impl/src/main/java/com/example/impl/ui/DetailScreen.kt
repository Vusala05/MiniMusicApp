package com.example.impl.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.core_ui.components.CoverImage
import com.example.impl.ui.components.PlayerBottomControls
import com.example.impl.ui.components.PlayerTopBar
import com.example.impl.ui.components.TrackInfoAndActions

@SuppressLint("SuspiciousIndentation")
@Composable
fun DetailScreen(
    state: DetailContract.State,
    onIntent: (DetailContract.Intent) -> Unit
) {
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                PlayerTopBar(
                    onBackClick = { onIntent(DetailContract.Intent.OnBackClick) },
                )

                CoverImage(
                    imageUrl = state.musicDetail.image,
                    contentDescription = null,
                    size = 300.dp,
                    roundedCornerShape = RoundedCornerShape(28.dp),
                    shadowElevation = 24.dp,
                )

                TrackInfoAndActions(
                    title = state.musicDetail.name,
                    artist = state.musicDetail.artistName,
                    downloadingStatus = state.uiDownloadStatus,
                    onDownloadClick = { onIntent(DetailContract.Intent.OnDownloadClicked) },
                    onLyricsClick = { onIntent(DetailContract.Intent.OnClickViewLyrics) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                PlayerBottomControls(
                    btnState = state.playbackState.btnState,
                    positionMs = state.dynamicPlayBackState.currentPosition,
                    hasNext = state.playbackState.hasNext,
                    durationMs = state.playbackState.totalDurationMs,
                    repeatMode = state.playbackState.repeatMode,
                    isShuffleOn = state.playbackState.isShuffleOn,
                    onSeek = { ms -> onIntent(DetailContract.Intent.OnSeek(ms)) },
                    onShuffleClick = { onIntent(DetailContract.Intent.OnShuffleClick) },
                    onPreviousClick = { onIntent(DetailContract.Intent.OnPreviousClick) },
                    onPlayPauseClick = { onIntent(DetailContract.Intent.OnClickPlayingBtn) },
                    onNextClick = { onIntent(DetailContract.Intent.OnNextClick) },
                    onRepeatClick = { onIntent(DetailContract.Intent.OnRepeatClick) }
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }



