package com.ads.applovin.nativead.loader

import android.util.Log
import com.ads.applovin.nativead.model.ApplovinNativeConfig
import com.ads.applovin.nativead.presenter.MaxNativeAdPresenter
import com.ads.nativead.loader.NativeAdLoader
import com.ads.nativead.model.NativeConfig
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdRevenueListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.nativeAds.MaxNativeAdListener
import com.applovin.mediation.nativeAds.MaxNativeAdLoader
import com.applovin.mediation.nativeAds.MaxNativeAdView

class ApplovinNativeLoader : NativeAdLoader<MaxNativeAdPresenter> {

    override fun load(
        config: NativeConfig,
        onSuccess: (MaxNativeAdPresenter) -> Unit,
        onFailure: (String?) -> Unit
    ) {

        Log.d("ApplovinNativeLoader", "ApplovinNativeLoader load")

        if (config !is ApplovinNativeConfig) {
            Log.d("ApplovinNativeLoader", "return by NativeConfig must be ApplovinNativeConfig")
            onFailure("NativeConfig must be ApplovinNativeConfig")
            return
        }
        val nativeAdLoader = MaxNativeAdLoader(config.adUnitId)
        nativeAdLoader.setRevenueListener(object : MaxAdRevenueListener {
            override fun onAdRevenuePaid(p0: MaxAd) {

            }
        })
        nativeAdLoader.setNativeAdListener(object : MaxNativeAdListener() {

            override fun onNativeAdLoadFailed(adUnitId: String, error: MaxError) {
                super.onNativeAdLoadFailed(adUnitId, error)
                Log.e(
                    "MAX",
                    "Quảng cáo không tải được: " + adUnitId + "code: ${error.code}, Lỗi: " + error.message
                )
                val waterfall = error.waterfall
                if (waterfall != null) {
                    Log.e("MAX", "Waterfall Name: " + waterfall.name)
                    Log.e("MAX", "Waterfall Latency: " + waterfall.latencyMillis + "ms")
                    for (response in waterfall.networkResponses) {
                        Log.e(
                            "MAX", "Network: " + response.mediatedNetwork.name +
                                    ", Ad Load State: " + response.adLoadState +
                                    ", Latency: " + response.latencyMillis + "ms" +
                                    ", Error: " + response.error.message
                        )
                    }
                }
                onFailure(error.message)
            }

            override fun onNativeAdLoaded(maxNativeAdView: MaxNativeAdView?, maxAd: MaxAd) {
                super.onNativeAdLoaded(maxNativeAdView, maxAd)
                Log.d("ApplovinNativeLoader", "onNativeAdLoaded: $maxAd")
                onSuccess(MaxNativeAdPresenter(nativeAdLoader, maxAd))
            }
        })

        nativeAdLoader.loadAd()

    }

}