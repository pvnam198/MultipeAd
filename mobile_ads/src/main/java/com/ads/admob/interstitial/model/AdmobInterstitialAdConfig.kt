package com.ads.admob.interstitial.model

import android.content.Context
import com.ads.interstitial.model.InterstitialAdConfig

class AdmobInterstitialAdConfig(
    override val adUnitId: String,
    val context: Context,
    val shouldLoad: Boolean
) : InterstitialAdConfig