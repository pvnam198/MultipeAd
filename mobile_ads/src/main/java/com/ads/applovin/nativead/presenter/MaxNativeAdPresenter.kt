package com.ads.applovin.nativead.presenter

import com.ads.nativead.presenter.NativeAdPresenter
import com.ads.nativead.presenter.NativeAdPresenterConfig
import com.applovin.mediation.MaxAd
import com.applovin.mediation.nativeAds.MaxNativeAdLoader

class MaxNativeAdPresenter(
    private val maxNativeAdLoader: MaxNativeAdLoader,
    private val maxAd: MaxAd
) : NativeAdPresenter {

    override fun show(
        config: NativeAdPresenterConfig,
        onFailure: (msg: String?) -> Unit
    ) {
        if (config !is MaxNativeAdPresenterConfig) {
            onFailure("MaxNativeAdPresenterConfig must be MaxNativeAdPresenterConfig")
            return
        }

        if (!config.shouldShow) {
            onFailure("Should not show")
            return
        }

        val maxNativeAdView = config.adContainer.maxNativeAdView
            ?: return onFailure("CustomMaxNativeView maybe not call setAdLayout()")

        maxNativeAdLoader.render(maxNativeAdView, maxAd)

    }

}