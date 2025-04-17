package com.ads.applovin.banner

import android.view.View
import com.ads.banner.model.BannerDestroyable
import com.ads.banner.model.BannerResult

class ApplovinBannerResult(
    val adView: View,
    val onDestroy: () -> Unit
) : BannerResult, BannerDestroyable {
    override fun destroy() = onDestroy()
}