package com.ads.applovin.open

import com.ads.admob.interstitial.presenter.AdmobOpenAdPresenterConfig
import com.ads.open.presenter.OpenAdPresenter
import com.ads.open.presenter.OpenAdPresenterConfig
import com.applovin.mediation.ads.MaxAppOpenAd

class ApplovinOpenAdPresenter(
    private val appOpenAd: MaxAppOpenAd,
) : OpenAdPresenter {

    private var config: OpenAdPresenterConfig? = null

    override fun canShow(config: OpenAdPresenterConfig): Boolean {
        return config is AdmobOpenAdPresenterConfig
    }

    override fun show(config: OpenAdPresenterConfig, onComplete: () -> Unit) {

        if (config !is ApplovinOpenAdPresenterConfig) {
            config.onShowAdComplete("OpenAdPresenterConfig must be ApplovinOpenAdPresenterConfig")
            return
        }

        appOpenAd.showAd()
    }

    fun onAdHidden() {
        config?.onShowAdComplete("onAdHidden")
    }

    fun onAdDisplayFailed() {
        config?.onShowAdComplete("onAdDisplayFailed")
    }

}