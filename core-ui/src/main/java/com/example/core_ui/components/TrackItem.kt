package com.example.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_data.util.ButtonState
import com.example.core_ui.R
import com.google.android.material.progressindicator.CircularProgressIndicator

@Composable
fun TrackItem(
    trackId : String,
    isChosen : Boolean,
    btnState : ButtonState,
    imageUrl : String,
    trackName : String,
    artistName : String,
    onTrackClick: (String) -> Unit,
    onIconClick : (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundModifier = if (isChosen) {
        Modifier.background(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(0xFF4A154B),
                    Color(0xFF80154B)
                )
            ),
            shape = RoundedCornerShape(16.dp)
        )
    } else {
        Modifier.background(
            color = Color(0xFF1E1B2E).copy(alpha = 0.4f),
            shape = RoundedCornerShape(16.dp)
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(backgroundModifier)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onTrackClick(trackId) }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CoverImage(
            imageUrl = imageUrl,
            contentDescription = null,
            size = 52.dp
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = trackName,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                ),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = artistName,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                color = Color.LightGray.copy(alpha = 0.7f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onIconClick(trackId) },
            contentAlignment = Alignment.Center
        ) {
            when (btnState) {
                ButtonState.LOADING -> CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
                ButtonState.PAUSE -> Icon(
                    painter = painterResource(R.drawable.pause),
                    contentDescription = "Pause",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                ButtonState.PLAY -> Icon(
                    painter = painterResource(R.drawable.play),
                    contentDescription = "Play",
                    tint = Color.LightGray.copy(alpha = 0.8f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        }


    }
