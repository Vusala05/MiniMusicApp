package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_data.util.ButtonState
import com.example.core_data.util.RepeatMode
import com.example.core_data.util.msToFormattedDuration
import com.example.core_ui.R

@Composable
fun PlayerBottomControls(
    btnState : ButtonState,
    positionMs: Int,
    durationMs: Int,
    onSeek: (Long) -> Unit,
    onShuffleClick: () -> Unit,
    isShuffleOn : Boolean,
    repeatMode: RepeatMode,
    onPreviousClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onRepeatClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var dragValue by remember { mutableStateOf<Float?>(null) }

    val maxValue = durationMs.toFloat().coerceAtLeast(1f)
    val shownValue = (dragValue ?: positionMs.toFloat()).coerceIn(0f, maxValue)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Slider(
            value = shownValue,
            valueRange = 0f..maxValue,
            enabled = durationMs > 0,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color(0xFF522382),
                inactiveTrackColor = Color.White.copy(alpha = 0.2f)
            ),
            onValueChange = { dragValue = it },
            onValueChangeFinished = {
                dragValue?.let { onSeek(it.toLong()) }
                dragValue = null
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = shownValue.toInt().msToFormattedDuration(),
                fontSize = 12.sp,
                color = Color.LightGray.copy(alpha = 0.7f),
                fontWeight = FontWeight.Medium
            )
            Text(
                text = durationMs.msToFormattedDuration(),
                fontSize = 12.sp,
                color = Color.LightGray.copy(alpha = 0.7f),
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {onShuffleClick()}) {
                Icon(
                    painter = painterResource(if(isShuffleOn)R.drawable.shuffle else R.drawable.shuffle_off) ,
                    contentDescription = "Shuffle",
                    tint = Color.Unspecified
                )
            }

            IconButton(onClick = onPreviousClick) {
                Icon(
                    painter = painterResource(R.drawable.previous),
                    contentDescription = "Previous",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF522382))
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onPlayPauseClick()},
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

            IconButton(onClick = onNextClick) {
                Icon(
                    painter =  painterResource(R.drawable.next),
                    contentDescription = "Next",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            IconButton(onClick = onRepeatClick) {
                Icon(
                    painterResource(when (repeatMode) {
                        RepeatMode.REPEAT_MODE_ALL -> R.drawable.repeat
                        RepeatMode.REPEAT_MODE_ONE -> R.drawable.repeat_one
                        else -> R.drawable.repeat_off
                    })
                        ,
                    contentDescription = "Repeat Mode",
                    tint = Color.Unspecified
                )
            }
        }
    }
}

