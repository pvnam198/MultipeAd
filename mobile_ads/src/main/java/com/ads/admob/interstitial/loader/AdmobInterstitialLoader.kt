package com.ads.admob.interstitial.loader

import android.util.Log
import com.ads.admob.interstitial.model.AdmobInterstitialAdConfig
import com.ads.interstitial.loader.InterstitialLoader
import com.ads.interstitial.model.InterstitialAdConfig
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class AdmobInterstitialLoader : InterstitialLoader<InterstitialAd> {

    companion object {

        private const val TAG = "AdmobInterstitialLoader"

    }

    override fun fetchInterstitialAd(
        config: InterstitialAdConfig,
        onSuccess: (InterstitialAd) -> Unit,
        onFailure: (String?) -> Unit
    ) {

        Log.d(TAG, "fetchInterstitialAd")

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
                    Log.d(TAG, "onAdLoaded: ")
                    onSuccess(ad)
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    super.onAdFailedToLoad(error)
                    Log.d(TAG, "onAdFailedToLoad: ")
                    onFailure(error.message)
                }
            }
        )
    }
}