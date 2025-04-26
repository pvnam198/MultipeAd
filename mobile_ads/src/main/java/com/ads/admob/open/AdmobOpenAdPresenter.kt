package com.ads.admob.open

import com.ads.admob.interstitial.presenter.AdmobOpenAdPresenterConfig
import com.ads.open.presenter.OpenAdPresenter
import com.ads.open.presenter.OpenAdPresenterConfig
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.appopen.AppOpenAd

class AdmobOpenAdPresenter(
    private val appOpenAd: AppOpenAd
) : OpenAdPresenter {

    override fun canShow(config: OpenAdPresenterConfig): Boolean {
        return config is AdmobOpenAdPresenterConfig
    }

    override fun show(config: OpenAdPresenterConfig, onComplete: () -> Unit) {

        if (config !is AdmobOpenAdPresenterConfig) {
            config.onShowAdComplete("OpenAdPresenterConfig must be AdmobOpenAdPresenterConfig")
            return
        }

        appOpenAd.show(config.activity)
        appOpenAd.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                config.onShowAdComplete("onAdDismissedFullScreenContent")
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                super.onAdFailedToShowFullScreenContent(p0)
                config.onShowAdComplete("onAdFailedToShowFullScreenContent, msg: ${p0.message}")
            }
        }
    }

}