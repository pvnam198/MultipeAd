package com.ads.applovin.banner

import android.view.View
import com.ads.banner.model.banner.BannerDestroyable
import com.ads.banner.model.banner.BannerResult

class ApplovinBannerResult(
    val adView: View,
    val onDestroy: () -> Unit
) : BannerResult, BannerDestroyable {
    override fun destroy() = onDestroy()
}