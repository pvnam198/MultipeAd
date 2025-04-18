package com.ads.admob.interstitial.loader

import com.ads.admob.interstitial.model.AdmobInterstitialAdConfig
import com.ads.interstitial.loader.InterstitialLoader
import com.ads.interstitial.model.InterstitialAdConfig
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class AdmobInterstitialLoader : InterstitialLoader<InterstitialAd> {

    override fun fetchInterstitialAd(
        config: InterstitialAdConfig,
        onSuccess: (InterstitialAd) -> Unit,
        onFailure: (String?) -> Unit
    ) {

        if (config !is AdmobInterstitialAdConfig) {
            onFailure("InterstitialAdConfig must be AdmobInterstitialAdConfig")
            return
        }

        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            config.context,
            config.adUnitId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    super.onAdLoaded(ad)
                    onSuccess(ad)
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    super.onAdFailedToLoad(error)
                    onFailure(error.message)
                }
            }
        )
    }
}