package com.ads.applovin.interstitial.model

import com.ads.interstitial.model.InterstitialAdConfig

class ApplovinInterstitialAdConfig(
    override val adUnitId: String,
    val shouldLoad: Boolean
) : InterstitialAdConfig