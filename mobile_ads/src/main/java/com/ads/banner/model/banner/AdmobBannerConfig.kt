package com.ads.banner.model.banner

import com.ads.banner.model.BannerSize

data class AdmobBannerConfig(
    override val adUnitId: String,
    val adSize: BannerSize = BannerSize.Banner,
    val isCollapsible: Boolean = false
) : BannerAdConfig