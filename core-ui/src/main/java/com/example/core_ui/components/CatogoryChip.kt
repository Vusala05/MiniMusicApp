package com.example.core_ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor =
         if (isSelected) Color.White else Color.Transparent

    val textColor = if (isSelected) Color.Black else Color.White



    val borderModifier = if (isSelected) {
        Modifier
    } else {
        Modifier.border(
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.15f)),
            shape = CircleShape
        )
    }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .then(borderModifier)
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 15.sp
            ),
            color = textColor
        )
    }
}
@Preview(showBackground = true, backgroundColor = 0xFF0D0B14)
@Composable
fun CategoryChipPreview() {
    CategoryChip(
        text = "Instrument",
        isSelected = false,
        onClick = {}
    )
}


@Preview(showBackground = true, backgroundColor = 0xFF0D0B14)
@Composable
fun SelectedCategoryChipPreview() {
    CategoryChip(
        text = "Instrument",
        isSelected = true,
        onClick = {}
    )
}