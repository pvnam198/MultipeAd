package com.ads.admob.banner

import com.ads.banner.model.BannerSize
import com.ads.banner.model.BannerAdConfig

data class AdmobBannerConfig(
    override val adUnitId: String,
    val adSize: BannerSize = BannerSize.Banner,
    val isCollapsible: Boolean = false
) : BannerAdConfig