package com.ads.applovin.open

import com.ads.open.loader.OpenAdConfig
import com.ads.open.loader.OpenAdLoader
import com.ads.open.presenter.OpenAdPresenter
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAppOpenAd

class ApplovinOpenAdLoader : OpenAdLoader {

    override fun load(
        adConfig: OpenAdConfig,
        onSuccess: (OpenAdPresenter) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        if (adConfig !is ApplovinOpenAdConfig) {
            onFailure("OpenAdConfig must be ApplovinOpenAdConfig")
            return
        }

        val appOpenAd = MaxAppOpenAd(adConfig.adUnitId)
        var presenter: ApplovinOpenAdPresenter?= null
        appOpenAd.setListener(object : MaxAdListener {
            override fun onAdLoaded(p0: MaxAd) {
                presenter = ApplovinOpenAdPresenter(appOpenAd)
                onSuccess(presenter)
            }

            override fun onAdDisplayed(p0: MaxAd) {
            }

            override fun onAdHidden(maxAd: MaxAd) {
                presenter?.onAdHidden()
            }

            override fun onAdClicked(p0: MaxAd) {
            }

            override fun onAdLoadFailed(p0: String, p1: MaxError) {
                onFailure(p1.message)
            }

            override fun onAdDisplayFailed(
                p0: MaxAd,
                p1: MaxError
            ) {
                presenter?.onAdDisplayFailed()
            }
        })
    }
}