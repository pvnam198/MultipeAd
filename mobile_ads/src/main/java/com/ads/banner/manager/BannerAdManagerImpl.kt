package com.ads.banner.manager

import android.content.Context
import com.ads.AdNetworkType
import com.ads.admob.banner.AdmobBannerLoader
import com.ads.admob.banner.AdmobBannerResult
import com.ads.applovin.banner.ApplovinBannerLoader
import com.ads.applovin.banner.ApplovinBannerResult
import com.ads.banner.model.banner.BannerAdConfig
import com.ads.banner.model.banner.BannerResult
import com.google.android.gms.ads.AdView

class BannerAdManagerImpl(
    private val context: Context,
    private val adNetworkPriorities: List<AdNetworkType>
) : BannerAdManager {

    override fun fetchBannerAd(
        config: BannerAdConfig,
        onSuccess: (BannerResult) -> Unit,
        onFailure: (String?) -> Unit
    ) {

        val iterator = adNetworkPriorities.iterator()
        tryNextAdNetwork(iterator, config, onSuccess, onFailure)
    }

    private fun tryNextAdNetwork(
        iterator: Iterator<AdNetworkType>,
        config: BannerAdConfig,
        onSuccess: (BannerResult) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        if (!iterator.hasNext()) {
            onFailure("All ad networks failed")
            return
        }

        when (iterator.next()) {
            AdNetworkType.ADMOB -> {
                loadAdmobBanner(config, onSuccess) { error ->
                    tryNextAdNetwork(iterator, config, onSuccess, onFailure)
                }
            }
            AdNetworkType.APPLOVIN -> {
                loadApplovinBanner(config, onSuccess) { error ->
                    tryNextAdNetwork(iterator, config, onSuccess, onFailure)
                }
            }
        }
    }

    private fun loadApplovinBanner(
        config: BannerAdConfig,
        onSuccess: (BannerResult) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        ApplovinBannerLoader().fetchBannerAd(config, onSuccess = { ad ->
            val applovinBannerResult =
                ApplovinBannerResult(ad, onDestroy = { ad.destroy() })
            onSuccess(applovinBannerResult)
        }, onFailure)
    }

    private fun loadAdmobBanner(
        config: BannerAdConfig,
        onSuccess: (BannerResult) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        AdmobBannerLoader(context).fetchBannerAd(config, onSuccess = { bannerAd ->
            val admobBannerResult = getAdmobBannerResult(bannerAd)
            onSuccess(admobBannerResult)
        }, onFailure)
    }

    private fun getAdmobBannerResult(bannerAd: AdView): AdmobBannerResult =
        AdmobBannerResult(
            adView = bannerAd,
            onResume = { bannerAd.resume() },
            onPause = { bannerAd.pause() },
            onDestroy = { bannerAd.destroy() }
        )
}