package com.ads.nativead.model

import com.ads.nativead.presenter.NativeAdPresenter
import com.ads.nativead.presenter.NativeAdPresenterConfig

interface DisplayableNativeAd {

    companion object {

        fun show(
            presenter: NativeAdPresenter,
            config: NativeAdPresenterConfig,
            onFailure: (String?) -> Unit
        ) {
            if (presenter !is DisplayableNativeAd) {
                onFailure("NativeAdPresenter must be DisplayableNativeAd")
                return
            }
            presenter.show(config = config) {
                onFailure(it)
            }
        }

    }

    fun show(config: NativeAdPresenterConfig, onFailure: (msg: String?) -> Unit)
}