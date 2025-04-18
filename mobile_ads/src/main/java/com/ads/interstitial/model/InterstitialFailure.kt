package com.ads.interstitial.model

import com.ads.model.AdNetworkType

class InterstitialFailure(
    val adNetworkType: AdNetworkType,
    val message: String?
)