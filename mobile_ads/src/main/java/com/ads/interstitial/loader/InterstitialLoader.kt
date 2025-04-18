package com.ads.interstitial.loader

import com.ads.interstitial.model.InterstitialAdConfig

interface InterstitialLoader<T> {

    fun fetchInterstitialAd(
        config: InterstitialAdConfig,
        onSuccess: (T) -> Unit,
        onFailure: (msg: String?) -> Unit
    )

}