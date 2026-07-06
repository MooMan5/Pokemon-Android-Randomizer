package com.dabomstew.pkrandomandroid.util

import android.content.Context
import android.net.Uri
import java.io.File

object FileUtil {

    fun copyUriToTempFile(context: Context, uri: Uri, prefix: String): File {
        val suffix = getExtensionFromUri(context, uri) ?: ""
        val tempFile = File.createTempFile(prefix, suffix, context.cacheDir)
        context.contentResolver.openInputStream(uri)?.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        } ?: throw IllegalStateException("Cannot open input stream for URI: $uri")
        return tempFile
    }

    fun getFileNameFromUri(context: Context, uri: Uri): String {
        var result = ""
        if (uri.scheme == "content") {
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                    if (columnIndex >= 0) {
                        result = cursor.getString(columnIndex) ?: ""
                    }
                }
            }
        }
        if (result.isEmpty()) {
            result = uri.path?.substringAfterLast('/') ?: uri.toString()
        }
        return result
    }

    private fun getExtensionFromUri(context: Context, uri: Uri): String? {
        val name = getFileNameFromUri(context, uri)
        val dot = name.lastIndexOf('.')
        return if (dot >= 0) name.substring(dot) else null
    }
}
