package com.ads.applovin.nativead.presenter

import com.ads.model.AdUnitIdProvider
import com.ads.nativead.listener.NativeAdCloseListener
import com.ads.nativead.model.DisplayableNativeAd
import com.ads.nativead.presenter.CloseableNativeAd
import com.ads.nativead.presenter.NativeAdPresenter
import com.ads.nativead.presenter.NativeAdPresenterConfig
import com.ads.open.presenter.ReleasableNativeAd
import com.applovin.mediation.MaxAd
import com.applovin.mediation.nativeAds.MaxNativeAdLoader

class MaxNativeAdPresenter(
    private val maxNativeAdLoader: MaxNativeAdLoader,
    private val maxAd: MaxAd,
    override val adUnitId: String
) : NativeAdPresenter,
    DisplayableNativeAd,
    CloseableNativeAd,
    ReleasableNativeAd,
    AdUnitIdProvider {

    override var adCloseListener: NativeAdCloseListener? = null

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

    override fun onAdClosed() {
        adCloseListener?.onAdClosed()
    }

    override fun release() {
        maxNativeAdLoader.destroy(maxAd)
    }

}