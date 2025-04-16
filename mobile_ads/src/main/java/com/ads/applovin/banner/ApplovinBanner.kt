package com.ads.applovin.banner

import com.ads.banner.model.BannerAd
import com.ads.banner.model.BannerDestroyable
import com.applovin.mediation.ads.MaxAdView

class ApplovinBanner(
    private val adView: MaxAdView
) : BannerAd<MaxAdView>(adView), BannerDestroyable {

    override fun destroy() {
        adView.destroy()
    }

}