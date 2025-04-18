package com.ads.admob.banner

import android.content.Context
import com.ads.banner.model.BannerAdConfig
import com.ads.banner.model.BannerSize

data class AdmobBannerConfig(
    override val adUnitId: String,
    val context: Context,
    val adSize: BannerSize = BannerSize.Banner,
    val isCollapsible: Boolean = false
) : BannerAdConfig