package com.ads.applovin.nativead.presenter

import com.ads.nativead.presenter.NativeAdPresenter
import com.ads.nativead.presenter.NativeAdPresenterConfig
import com.applovin.mediation.nativeAds.MaxNativeAd

class ApplovinNativeAdPresenter(
    private val maxNativeAd: MaxNativeAd
) : NativeAdPresenter {
    override fun show(
        config: NativeAdPresenterConfig,
        onFailure: () -> Unit
    ) {

    }
}