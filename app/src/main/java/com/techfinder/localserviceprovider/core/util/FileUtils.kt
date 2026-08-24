package com.techfinder.localserviceprovider.core.util

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

fun Context.createTempImageUri(): Uri {
    val tempFile = File.createTempFile("profile_image_", ".jpg", cacheDir).apply {
        createNewFile()
        deleteOnExit()
    }
    return FileProvider.getUriForFile(
        this,
        "${packageName}.fileprovider",
        tempFile
    )
}