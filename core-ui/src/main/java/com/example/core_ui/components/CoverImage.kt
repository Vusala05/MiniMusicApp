package com.example.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.core_ui.R

@Composable
fun CoverImage(
    imageUrl: String,
    contentDescription: String?,
    size: Dp,
    roundedCornerShape: RoundedCornerShape = RoundedCornerShape(12.dp),
    shadowElevation: Dp = 0.dp,
    modifier: Modifier = Modifier
) {

        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.music_sync),
            error = painterResource(R.drawable.music_sync) ,
            modifier = modifier
                .size(size)
                .shadow(
                    elevation = shadowElevation,
                    shape = roundedCornerShape,
                    ambientColor = Color(0xFF80154B),
                    spotColor = Color(0xFF80154B)
                )
                .clip(roundedCornerShape)
                .background(Color.DarkGray)
        )
    }


@Preview(showBackground = true, backgroundColor = 0xFF0D0B14)
@Composable
fun PlayerCoverImagePreview() {
    CoverImage(
        imageUrl = "",
        contentDescription = "Cover Image",
        size = 300.dp,
    )
}