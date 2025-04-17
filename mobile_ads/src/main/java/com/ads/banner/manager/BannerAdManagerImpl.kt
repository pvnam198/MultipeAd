package com.ads.banner.manager

import android.content.Context
import com.ads.AdNetworkType
import com.ads.admob.banner.AdmobBannerLoader
import com.ads.admob.banner.AdmobBannerResult
import com.ads.applovin.banner.ApplovinBannerLoader
import com.ads.applovin.banner.ApplovinBannerResult
import com.ads.banner.model.banner.BannerAd
import com.ads.banner.model.banner.BannerDestroyable
import com.ads.banner.model.banner.BannerPauseAble
import com.ads.banner.model.banner.BannerResumeAble
import com.ads.banner.model.banner.BannerAdConfig
import com.ads.banner.model.banner.BannerResult
import com.google.android.gms.ads.AdView

class BannerAdManagerImpl(
    private val context: Context,
    private val adNetworkType: AdNetworkType
) : BannerAdManager {

    override fun fetchBannerAd(
        config: BannerAdConfig,
        onSuccess: (BannerResult) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        when (adNetworkType) {
            AdNetworkType.ADMOB -> {
                loadAdmobBanner(config, onSuccess, onFailure)
            }

            AdNetworkType.APPLOVIN -> {
                loadApplovinBanner(config, onSuccess, onFailure)
            }
        }
    }

    private fun loadApplovinBanner(
        config: BannerAdConfig,
        onSuccess: (BannerResult) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        ApplovinBannerLoader().fetchBannerAd(config, onSuccess = {
            val applovinBannerResult =
                ApplovinBannerResult(it.ad, onDestroy = { (it as? BannerDestroyable)?.destroy() })
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

    private fun getAdmobBannerResult(bannerAd: BannerAd<AdView>): AdmobBannerResult =
        AdmobBannerResult(
            adView = bannerAd.ad,
            onResume = { (bannerAd as? BannerResumeAble)?.resume() },
            onPause = { (bannerAd as? BannerPauseAble)?.pause() },
            onDestroy = { (bannerAd as? BannerDestroyable)?.destroy() }
        )
}