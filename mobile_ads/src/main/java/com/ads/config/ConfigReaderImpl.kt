package com.ads.config

import android.content.Context
import java.io.File
import java.util.Properties

class ConfigReaderImpl(
    private val context: Context
) : ConfigReader {

    override fun readProperty(key: String): String? {
        return try {
            val file = File(findRootDir(context), "local.properties")
            if (!file.exists()) return null
            val properties = Properties().apply { load(file.inputStream()) }
            properties.getProperty(key)
        } catch (_: Exception) {
            null
        }
    }

    private fun findRootDir(context: Context): File {
        var dir = context.filesDir
        while (dir.parentFile?.name != null && dir.parentFile?.name != "src") {
            dir = dir.parentFile!!
        }
        return dir.parentFile ?: context.filesDir
    }
}