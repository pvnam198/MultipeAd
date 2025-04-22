package com.ads.applovin.interstitial.presenter

import com.ads.interstitial.presenter.InterstitialAdPresenter
import com.ads.interstitial.presenter.InterstitialPresenterConfig
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd

class ApplovinInterstitialAdPresenter(
    private val interstitialAd: MaxInterstitialAd
) : InterstitialAdPresenter {

    override fun canShow(config: InterstitialPresenterConfig): Boolean {
        return config is ApplovinInterstitialPresenterConfig
    }

    override fun show(
        config: InterstitialPresenterConfig,
        onComplete: () -> Unit
    ) {
        if (config !is ApplovinInterstitialPresenterConfig) return onComplete()
        if (!config.shouldShow) return onComplete()
        interstitialAd.showAd(config.activity)
        interstitialAd.setListener(object : MaxAdListener{
            override fun onAdLoaded(p0: MaxAd) {

            }

            override fun onAdDisplayed(p0: MaxAd) {
            }

            override fun onAdHidden(p0: MaxAd) {
                onComplete()
            }

            override fun onAdClicked(p0: MaxAd) {
            }

            override fun onAdLoadFailed(p0: String, p1: MaxError) {
            }

            override fun onAdDisplayFailed(
                p0: MaxAd,
                p1: MaxError
            ) {
                onComplete()
            }

        })
    }
}