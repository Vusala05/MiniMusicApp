package com.example.core_data.util

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun String.toTime(): String {
    return try {
        val zonedDateTime = ZonedDateTime.parse(this)
            .withZoneSameInstant(ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
        zonedDateTime.format(formatter)
    } catch (e: Exception) {
        "--:--"
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.toDate(): String{
    return try {
        val zonedDateTime = ZonedDateTime.parse(this)
            .withZoneSameInstant(ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
        zonedDateTime.format(formatter)
    } catch (e: Exception) {
        "00/00/0000"
    }
}

@SuppressLint("DefaultLocale")
fun Int?.msToFormattedDuration(): String {
    val totalSeconds = ((this ?: 0).coerceAtLeast(0)) / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%d:%02d", minutes, seconds)
}