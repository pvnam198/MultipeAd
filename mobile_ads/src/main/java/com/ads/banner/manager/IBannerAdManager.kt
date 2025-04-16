package com.ads.banner.manager

import com.ads.banner.model.BannerAd
import com.ads.banner.model.BannerAdConfig

interface IBannerAdManager {

    fun fetchBannerAd(
        config: BannerAdConfig,
        onSuccess: (BannerAd<*>) -> Unit,
        onFailure: (msg: String?) -> Unit
    )

}