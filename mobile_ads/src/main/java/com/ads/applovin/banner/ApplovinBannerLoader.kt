package com.ads.applovin.banner

import android.content.Context
import com.ads.banner.loader.BannerLoader
import com.ads.banner.model.BannerAd
import com.ads.banner.model.BannerAdConfig
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView

class ApplovinBannerLoader(
    private val context: Context
) : BannerLoader<BannerAd<MaxAdView>> {


    override fun fetchBannerAd(
        config: BannerAdConfig,
        onSuccess: (BannerAd<MaxAdView>) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        val adView = MaxAdView(config.adUnitId, context)
        adView.setListener(object : MaxAdViewAdListener {
            override fun onAdLoaded(p0: MaxAd) {
                onSuccess(ApplovinBanner(adView))
            }

            override fun onAdDisplayed(p0: MaxAd) {}

            override fun onAdHidden(p0: MaxAd) {}

            override fun onAdClicked(p0: MaxAd) {}

            override fun onAdLoadFailed(p0: String, p1: MaxError) {
                val msgBuilder = StringBuilder()
                msgBuilder.append(p0).append(", ").append(p1.message)
                onFailure(msgBuilder.toString())
            }

            override fun onAdDisplayFailed(p0: MaxAd, p1: MaxError) {}

            override fun onAdExpanded(p0: MaxAd) {}

            override fun onAdCollapsed(p0: MaxAd) {}

        })
        adView.loadAd()
    }

}