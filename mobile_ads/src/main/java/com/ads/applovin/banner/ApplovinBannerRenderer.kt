package com.ads.applovin.banner

import android.view.View
import com.ads.banner.model.BannerResult
import com.ads.banner.render.BannerRenderer

class ApplovinBannerRenderer(
    private val onRender: (View) -> Unit
) : BannerRenderer {

    override fun canRender(bannerResult: BannerResult): Boolean {
        return bannerResult is ApplovinBannerResult
    }

    override fun render(bannerResult: BannerResult) {
        if (bannerResult is ApplovinBannerResult) {
            onRender(bannerResult.adView)
        }
    }
}