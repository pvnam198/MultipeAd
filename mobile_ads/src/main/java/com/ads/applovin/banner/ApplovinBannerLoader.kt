package com.ads.applovin.banner

import android.content.Context
import android.util.Log
import android.widget.FrameLayout
import com.ads.banner.loader.BannerLoader
import com.ads.banner.model.BannerAd
import com.ads.banner.model.banner.BannerAdConfig
import com.ads.banner.model.banner.MaxBannerConfig
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
        if (config !is MaxBannerConfig) {
            onFailure("BannerAdConfig must be of type MaxBannerConfig")
            return
        }

        Log.d("log_debug_123", "fetchBannerAd: ")
        val adView = MaxAdView(config.adUnitId, context).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )

            setListener(object : MaxAdViewAdListener {
                override fun onAdLoaded(p0: MaxAd) {
                    Log.d("log_debug_123", "onAdLoaded")
                    onSuccess(ApplovinBanner(this@apply))
                }

                override fun onAdDisplayed(p0: MaxAd) {
                    Log.d("log_debug_123", "onAdDisplayed")
                }

                override fun onAdHidden(p0: MaxAd) {
                    Log.d("log_debug_123", "onAdHidden")
                }

                override fun onAdClicked(p0: MaxAd) {
                    Log.d("log_debug_123", "onAdClicked")
                }

                override fun onAdLoadFailed(p0: String, p1: MaxError) {
                    Log.d("log_debug_123", "onAdLoadFailed: $p0, ${p1.message}")
                    onFailure("$p0, ${p1.message}")
                }

                override fun onAdDisplayFailed(p0: MaxAd, p1: MaxError) {
                    Log.d("log_debug_123", "onAdDisplayFailed: ${p1.message}")
                }

                override fun onAdExpanded(p0: MaxAd) {
                    Log.d("log_debug_123", "onAdExpanded")
                }

                override fun onAdCollapsed(p0: MaxAd) {
                    Log.d("log_debug_123", "onAdCollapsed")
                }
            })
        }

        config.parentView.addView(adView)
        adView.loadAd()
    }

}