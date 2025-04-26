package com.ads.admob.nativead.loader

import com.ads.admob.nativead.model.AdmobNativeConfig
import com.ads.admob.nativead.presenter.AdmobNativeAdPresenter
import com.ads.nativead.loader.NativeAdLoader
import com.ads.nativead.model.NativeConfig
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions

class AdmobNativeAdLoader : NativeAdLoader<AdmobNativeAdPresenter> {

    override fun load(
        config: NativeConfig,
        onSuccess: (AdmobNativeAdPresenter) -> Unit,
        onFailure: (String?) -> Unit
    ) {

        if (config !is AdmobNativeConfig) {
            onFailure("NativeConfig must be AdmobNativeConfig")
            return
        }

        var admobNativeAdPresenter: AdmobNativeAdPresenter? = null

        val adLoader = AdLoader.Builder(config.context, config.adUnitId)
            .forNativeAd { ad: NativeAd ->
                admobNativeAdPresenter =
                    AdmobNativeAdPresenter(nativeAd = ad, adUnitId = config.adUnitId)
                onSuccess(admobNativeAdPresenter)
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    onFailure(p0.message)
                }

                override fun onAdClosed() {
                    super.onAdClosed()
                    admobNativeAdPresenter?.onAdClosed()
                }

            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    .build()
            ).build()

        adLoader.loadAd(AdRequest.Builder().build())
    }
}