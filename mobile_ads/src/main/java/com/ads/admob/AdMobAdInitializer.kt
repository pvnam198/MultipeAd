package com.ads.admob

import android.content.Context
import com.ads.AdInitializer
import com.google.android.gms.ads.MobileAds

class AdMobAdInitializer(
    private val context: Context,
    private val onInitializeComplete: (() -> Unit)? = null
) : AdInitializer {
    override fun initialize() {
        MobileAds.initialize(context) {
            onInitializeComplete?.invoke()
        }
    }
}