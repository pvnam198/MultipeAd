package com.ads.admob.banner

import android.content.Context
import android.os.Bundle
import com.ads.banner.loader.BannerLoader
import com.ads.banner.model.banner.BannerAd
import com.ads.admob.banner.AdmobBannerConfig
import com.ads.banner.model.banner.BannerAdConfig
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

class AdmobBannerLoader(
    private val context: Context
) : BannerLoader<BannerAd<AdView>> {

    override fun fetchBannerAd(
        config: BannerAdConfig,
        onSuccess: (BannerAd<AdView>) -> Unit,
        onFailure: (String?) -> Unit
    ) {

        if (config !is AdmobBannerConfig) {
            onFailure("BannerAdConfig is must be AdmobBannerConfig")
            return
        }

        val adView = AdView(context)
        adView.setAdSize(AdSize(config.adSize.width, config.adSize.height))
        adView.adUnitId = config.adUnitId
        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                onSuccess(AdmobBanner(adView))
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                onFailure(adError.message)
            }
        }
        val adRequestBuilder = AdRequest.Builder()
        if (config.isCollapsible) {
            val extras = Bundle()
            extras.putString("collapsible", "bottom")
            adRequestBuilder.addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
        }
        adView.loadAd(adRequestBuilder.build())
    }

}