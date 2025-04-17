package com.ads.applovin.banner

import android.view.ViewGroup
import com.ads.banner.model.BannerAdConfig

data class ApplovinBannerConfig(
    override val adUnitId: String,
    val parentView: ViewGroup
) : BannerAdConfig