package com.ads.admob.banner

import android.view.View
import com.ads.banner.model.banner.BannerResult
import com.ads.banner.render.BannerRenderer

class AdmobBannerRenderer(
    private val onRender: (View) -> Unit
) : BannerRenderer {
    override fun canRender(bannerResult: BannerResult): Boolean {
        return bannerResult is AdmobBannerResult
    }

    override fun render(bannerResult: BannerResult) {
        if (bannerResult is AdmobBannerResult) {
            onRender(bannerResult.adView)
        }
    }

}