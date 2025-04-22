package com.ads.nativead.presenter

interface NativeAdPresenter {
    fun show(config: NativeAdPresenterConfig, onFailure: (msg: String?) -> Unit)
}