package com.ads.applovin.banner

import android.widget.FrameLayout
import com.ads.banner.loader.BannerLoader
import com.ads.banner.model.banner.BannerAdConfig
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView

class ApplovinBannerLoader() : BannerLoader<MaxAdView> {

    override fun fetchBannerAd(
        config: BannerAdConfig,
        onSuccess: (MaxAdView) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        if (config !is ApplovinBannerConfig) {
            onFailure("BannerAdConfig must be of type MaxBannerConfig")
            return
        }

        val adView = MaxAdView(config.adUnitId).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
        }
        adView.setListener(object : MaxAdViewAdListener {
            override fun onAdLoaded(p0: MaxAd) {
                onSuccess(adView)
            }

            override fun onAdDisplayed(p0: MaxAd) {
            }

            override fun onAdHidden(p0: MaxAd) {
            }

            override fun onAdClicked(p0: MaxAd) {
            }

            override fun onAdLoadFailed(p0: String, p1: MaxError) {
                onFailure("$p0, ${p1.message}")
            }

            override fun onAdDisplayFailed(p0: MaxAd, p1: MaxError) {
            }

            override fun onAdExpanded(p0: MaxAd) {
            }

            override fun onAdCollapsed(p0: MaxAd) {
            }
        })
        config.parentView.addView(adView)
        adView.loadAd()
    }

}