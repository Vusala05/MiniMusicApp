package com.example.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun AlbumItem(
    albumId : String,
    imageUrl : String,
    albumTitle : String,
    artistTitle : String,
    time : String,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(150.dp)
            .clickable { onItemClick(albumId) }
    ) {
        CoverImage(
            imageUrl =  imageUrl,
            contentDescription = null,
            size = 150.dp,
            roundedCornerShape = RoundedCornerShape(15.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = albumTitle,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "$artistTitle - $time",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun AlbumItemPreview() {

    AlbumItem(
        albumId = "",
        imageUrl = "https://usercontent.jamendo.com?type=album&id=368084&width=300",
        albumTitle = "Afterglow",
        artistTitle = "Luna Park",
        time = "2024",
        onItemClick = {}
    )
}