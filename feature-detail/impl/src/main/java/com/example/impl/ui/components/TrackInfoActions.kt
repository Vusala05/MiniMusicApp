package com.example.impl.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_domain.model.UiDownloadingState
import com.example.core_ui.R

@Composable
fun TrackInfoAndActions(
    title: String,
    artist: String,
    onDownloadClick: () -> Unit,
    downloadingStatus: UiDownloadingState,
    onLyricsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = artist,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 15.sp),
            color = Color.LightGray.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            contentAlignment = Alignment.Center
        ) {

            Spacer(modifier = Modifier.width(12.dp))
            if (downloadingStatus != UiDownloadingState.Hidden) {
                Button(
                onClick = onDownloadClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF522382)
                ),
                shape = RoundedCornerShape(22.dp),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
            ){
                when (downloadingStatus) {
                    is UiDownloadingState.InProgress -> {
                        val downloadingProgress = downloadingStatus.progress
                        if (downloadingProgress != null) {
                            CircularProgressIndicator(
                                progress = { downloadingProgress / 100f },
                                modifier = Modifier.size(18.dp),
                                color = Color.White, strokeWidth = 2.dp
                            )
                        } else {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                color = Color.White, strokeWidth = 2.dp
                            )
                        }
                        Spacer(Modifier.width(6.dp))
                        Text(downloadingProgress?.let { "$it% · Cancel" } ?: "Queued · Cancel",
                            color = Color.White)
                    }
                    UiDownloadingState.Completed -> {
                        Icon(painterResource(R.drawable.download), "Downloaded", Modifier.size(18.dp), Color.White)
                        Spacer(Modifier.width(6.dp))
                        Text("Downloaded", color = Color.White)
                    }
                    else -> {
                        Icon(painterResource(R.drawable.download), "Download", Modifier.size(18.dp), Color.White)
                        Spacer(Modifier.width(6.dp))
                        Text("Download", color = Color.White)
                    }
                }
            }
        }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White.copy(alpha = 0.08f))
                .clickable { onLyricsClick() }
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.music) ,
                contentDescription = "Lyrics",
                tint = Color.Unspecified,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "View Lyrics",
                color = Color.LightGray,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0D0B14)
@Composable
fun TrackInfoAndActionsPreview() {
    TrackInfoAndActions(
        title = "Midnight Dreams",
        artist = "Neon Aurora",
        onDownloadClick = {},
        onLyricsClick = {},
        downloadingStatus = UiDownloadingState.InProgress(100)
    )
}