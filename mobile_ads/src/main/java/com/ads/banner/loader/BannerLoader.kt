package com.ads.banner.loader

import com.ads.banner.model.banner.BannerAdConfig

interface BannerLoader<T> {
    fun fetchBannerAd(
        config: BannerAdConfig,
        onSuccess: (T) -> Unit,
        onFailure: (msg: String?) -> Unit
    )
}