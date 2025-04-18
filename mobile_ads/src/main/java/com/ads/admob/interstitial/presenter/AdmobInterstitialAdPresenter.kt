package com.ads.admob.interstitial.presenter

import com.ads.interstitial.presenter.InterstitialAdPresenter
import com.ads.interstitial.presenter.InterstitialPresenterConfig
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.interstitial.InterstitialAd

class AdmobInterstitialAdPresenter(
    private val interstitialAd: InterstitialAd
) : InterstitialAdPresenter {

    override fun canShow(config: InterstitialPresenterConfig): Boolean {
        return config is AdmobInterstitialPresenterConfig
    }

    override fun show(
        config: InterstitialPresenterConfig,
        onComplete: () -> Unit
    ) {
        if (config !is AdmobInterstitialPresenterConfig) return onComplete()
        if (!config.shouldShow) return onComplete()
        interstitialAd.show(config.activity)
        interstitialAd.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                onComplete()
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                super.onAdFailedToShowFullScreenContent(p0)
                onComplete()
            }
        }
    }

}