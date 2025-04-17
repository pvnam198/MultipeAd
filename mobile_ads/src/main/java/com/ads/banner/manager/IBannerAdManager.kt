package com.ads.banner.manager

import com.ads.banner.model.banner.BannerAdConfig
import com.ads.banner.model.banner.BannerResult

interface IBannerAdManager {

    fun fetchBannerAd(
        config: BannerAdConfig,
        onSuccess: (BannerResult) -> Unit,
        onFailure: (String?) -> Unit
    )

}