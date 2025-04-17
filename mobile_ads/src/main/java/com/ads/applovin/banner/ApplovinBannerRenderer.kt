package com.ads.applovin.banner

import android.view.View
import com.ads.banner.model.banner.BannerResult
import com.ads.banner.model.banner.render.BannerRenderer

class ApplovinBannerRenderer(
    private val onRender: (View) -> Unit
) : BannerRenderer {
    override fun render(bannerResult: BannerResult) {
        if (bannerResult is ApplovinBannerResult) {
            onRender(bannerResult.adView)
        }
    }
}