package com.ads.banner.manager

import android.content.Context
import com.ads.AdNetworkType
import com.ads.admob.banner.AdmobBannerLoader
import com.ads.applovin.banner.ApplovinBannerLoader
import com.ads.banner.model.BannerAd
import com.ads.banner.model.BannerAdConfig

class BannerAdManager(
    private val context: Context,
    private val adNetworkType: AdNetworkType
) : IBannerAdManager {

    override fun fetchBannerAd(
        config: BannerAdConfig,
        onSuccess: (BannerAd<*>) -> Unit,
        onFailure: (String?) -> Unit
    ) {
         when (adNetworkType) {
            AdNetworkType.ADMOB -> {
                AdmobBannerLoader(context).fetchBannerAd(config, onSuccess, onFailure)
            }

            AdNetworkType.APPLOVIN -> {
                ApplovinBannerLoader(context).fetchBannerAd(config, onSuccess, onFailure)
            }
        }
    }
}