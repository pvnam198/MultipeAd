package com.ads.banner.manager

import com.ads.banner.model.BannerResult

interface BannerAdManager {

    fun fetchBannerAd(
        onSuccess: (BannerResult) -> Unit,
        onFailure: (String?) -> Unit
    )

}