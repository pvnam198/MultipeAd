package com.ads.admob.nativead.loader

import android.util.Log
import com.ads.admob.nativead.model.AdmobNativeConfig
import com.ads.nativead.loader.NativeAdLoader
import com.ads.nativead.model.NativeConfig
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions

class AdmobNativeAdLoader : NativeAdLoader<NativeAd> {

    override fun load(
        config: NativeConfig,
        onSuccess: (NativeAd) -> Unit,
        onFailure: (String?) -> Unit
    ) {

        Log.d("log_test_123123131", "AdmobNativeAdLoader load")
        if (config !is AdmobNativeConfig) {
            Log.d("log_test_123123131", "AdmobNativeAdLoader load return by config !is AdmobNativeConfig")
            onFailure("NativeConfig must be AdmobNativeConfig")
            return
        }

        val adLoader = AdLoader.Builder(config.context, config.adUnitId)
            .forNativeAd { ad: NativeAd ->
                Log.d("log_test_123123131", "AdmobNativeAdLoader load forNativeAd: $ad")
                onSuccess(ad)
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    Log.d("log_test_123123131", "AdmobNativeAdLoader load onAdFailedToLoad: ${p0.message}")
                    onFailure(p0.message)
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    .build()
            ).build()

        adLoader.loadAd(AdRequest.Builder().build())
    }
}