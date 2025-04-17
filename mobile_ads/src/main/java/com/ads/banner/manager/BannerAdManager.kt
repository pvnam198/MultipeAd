package com.ads.banner.manager

import android.content.Context
import com.ads.AdNetworkType
import com.ads.admob.banner.AdmobBannerLoader
import com.ads.admob.banner.AdmobBannerResult
import com.ads.applovin.banner.ApplovinBannerLoader
import com.ads.applovin.banner.ApplovinBannerResult
import com.ads.banner.model.banner.BannerAdConfig
import com.ads.banner.model.banner.BannerResult

class BannerAdManager(
    private val context: Context,
    private val adNetworkType: AdNetworkType
) : IBannerAdManager {

    override fun fetchBannerAd(
        config: BannerAdConfig,
        onSuccess: (BannerResult) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        when (adNetworkType) {
            AdNetworkType.ADMOB -> {
                AdmobBannerLoader(context).fetchBannerAd(config, onSuccess = {
                    onSuccess(AdmobBannerResult(it.ad))
                }, onFailure)
            }

            AdNetworkType.APPLOVIN -> {
                ApplovinBannerLoader().fetchBannerAd(config, onSuccess = {
                    onSuccess(ApplovinBannerResult(it.ad))
                }, onFailure)
            }
        }
    }
}