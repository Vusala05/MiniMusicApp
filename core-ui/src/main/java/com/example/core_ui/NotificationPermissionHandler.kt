package com.example.core_ui


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.LocalActivity
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import androidx.compose.ui.platform.LocalContext

@Composable
fun NotificationPermissionHandler() {
    // Yalnız Android 13+ üçün lazımdır, aşağıda heç nə göstərmirik
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

    val context = LocalContext.current
    val activity = LocalActivity.current

    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                    == PackageManager.PERMISSION_GRANTED
        )
    }

    var showRationaleDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasPermission = isGranted
        if (!isGranted) {
            // İstifadəçi rədd etdi. "Bir daha soruşma"-nı seçib-seçmədiyini yoxla.
            val shouldShowRationale = activity?.shouldShowRequestPermissionRationale(
                Manifest.permission.POST_NOTIFICATIONS
            ) ?: false

            if (!shouldShowRationale) {
                // Sistem bir daha izah göstərmir → istifadəçi "bir daha soruşma" seçib
                // və ya ilk dəfə birbaşa rədd edib. Settings-ə yönləndirmə təklif et.
                showSettingsDialog = true
            }
        }
    }

    // İlk giriş: icazə yoxdursa, birbaşa sistem dialoqunu göstər
    LaunchedEffect(Unit) {
        if (!hasPermission) {
            val shouldShowRationale = activity?.shouldShowRequestPermissionRationale(
                Manifest.permission.POST_NOTIFICATIONS
            ) ?: false

            if (shouldShowRationale) {
                // İstifadəçi əvvəl bir dəfə rədd edib — əvvəlcə öz izahımızı göstərək
                showRationaleDialog = true
            } else {
                // İlk dəfədir — birbaşa sistem dialoqunu göstər
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    if (showRationaleDialog) {
        AlertDialog(
            onDismissRequest = { showRationaleDialog = false },
            title = { Text("Bildiriş icazəsi") },
            text = {
                Text(
                    "Çalınan mahnını idarə etmək (play/pause, next) üçün " +
                            "bildiriş icazəsi lazımdır. İcazə versəniz, mahnı bildiriş " +
                            "panelindən idarə oluna biləcək."
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    showRationaleDialog = false
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }) {
                    Text("İcazə ver")
                }
            },
            dismissButton = {
                TextButton(onClick = { showRationaleDialog = false }) {
                    Text("İndi yox")
                }
            }
        )
    }

    if (showSettingsDialog) {
        AlertDialog(
            onDismissRequest = { showSettingsDialog = false },
            title = { Text("Bildiriş icazəsi bağlıdır") },
            text = {
                Text(
                    "Bildiriş icazəsini əvvəllər rədd etmisiniz. Musiqi idarəetməsini " +
                            "bildirişdən istifadə etmək üçün Ayarlar-dan icazəni aça bilərsiniz."
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    showSettingsDialog = false
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }) {
                    Text("Ayarlara get")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSettingsDialog = false }) {
                    Text("Ləğv et")
                }
            }
        )
    }
}