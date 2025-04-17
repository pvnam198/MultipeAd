package com.ads.admob.banner

import android.view.View
import com.ads.banner.model.banner.BannerResult
import com.ads.banner.model.banner.render.BannerRenderer

class AdmobBannerRenderer(
    private val onRender: (View) -> Unit
) : BannerRenderer {

    override fun render(bannerResult: BannerResult) {
        if (bannerResult is AdmobBannerResult) {
            onRender(bannerResult.adView)
        }
    }

}