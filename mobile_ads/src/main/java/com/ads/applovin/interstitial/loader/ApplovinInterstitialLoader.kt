package com.ads.applovin.interstitial.loader

import android.util.Log
import com.ads.applovin.interstitial.model.ApplovinInterstitialAdConfig
import com.ads.interstitial.loader.InterstitialLoader
import com.ads.interstitial.model.InterstitialAdConfig
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd

class ApplovinInterstitialLoader : InterstitialLoader<MaxInterstitialAd> {

    companion object {

        private const val TAG = "ApplovinInterstitialLoader"

    }

    override fun fetchInterstitialAd(
        config: InterstitialAdConfig,
        onSuccess: (MaxInterstitialAd) -> Unit,
        onFailure: (String?) -> Unit
    ) {

        Log.d(TAG, "fetchInterstitialAd: ")

        if (config !is ApplovinInterstitialAdConfig) {
            Log.d(TAG, "fetchInterstitialAd: return by config")
            onFailure("InterstitialAdConfig must be ApplovinInterstitialAdConfig")
            return
        }

        if (!config.shouldLoad){
            Log.d(TAG, "fetchInterstitialAd: shouldLoad is false")
            onFailure("InterstitialAdConfig shouldLoad is false")
            return
        }

        val interstitialAd = MaxInterstitialAd(config.adUnitId)
        interstitialAd.setListener(object : MaxAdListener{
            override fun onAdLoaded(p0: MaxAd) {
                Log.d(TAG, "fetchInterstitialAd: onAdLoaded")
                onSuccess(interstitialAd)
            }

            override fun onAdDisplayed(p0: MaxAd) {
            }

            override fun onAdHidden(p0: MaxAd) {
            }

            override fun onAdClicked(p0: MaxAd) {
            }

            override fun onAdLoadFailed(p0: String, p1: MaxError) {
                Log.d(TAG, "fetchInterstitialAd: onAdLoadFailed")
                onFailure(p1.message)
            }

            override fun onAdDisplayFailed(
                p0: MaxAd,
                p1: MaxError
            ) {

            }
        })
        interstitialAd.loadAd()
    }

}