package com.ads.banner.model.banner

import android.view.ViewGroup

data class MaxBannerConfig(
    override val adUnitId: String,
    val parentView: ViewGroup
) : BannerAdConfig