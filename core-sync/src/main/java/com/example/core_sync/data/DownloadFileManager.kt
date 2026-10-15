package com.example.core_sync.data

import android.content.Context
import android.os.Environment
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class DownloadFileManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val baseDir: File
        get() = File(
            context.getExternalFilesDir(Environment.DIRECTORY_MUSIC) ?: context.filesDir,
            "downloads"
        ).apply { mkdirs() }

    fun readyFilePath(id: String) : File = File(baseDir,"$id.mp3")
    fun partFilePath(id: String) : File = File(baseDir,"$id.mp3.part")
    fun deleteFile(id: String){
        readyFilePath(id).delete()
        partFilePath(id).delete()
    }

}
