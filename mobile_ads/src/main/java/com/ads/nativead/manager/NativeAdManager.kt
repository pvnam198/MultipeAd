package com.ads.nativead.manager

import com.ads.nativead.presenter.NativeAdPresenter

interface NativeAdManager {

    fun load()

    fun getNativePresenter(): NativeAdPresenter?

}