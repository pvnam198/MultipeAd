package com.ads.config

interface ConfigReader {
    fun readProperty(key: String): String?
}