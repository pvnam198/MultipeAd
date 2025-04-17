package com.ads.admob.banner

import com.ads.banner.model.banner.BannerSize
import com.ads.banner.model.banner.BannerAdConfig

data class AdmobBannerConfig(
    override val adUnitId: String,
    val adSize: BannerSize = BannerSize.Banner,
    val isCollapsible: Boolean = false
) : BannerAdConfig