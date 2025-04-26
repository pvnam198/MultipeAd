package com.ads.nativead.manager

import com.ads.nativead.model.getter.NativeAdGetter
import com.ads.nativead.presenter.NativeAdPresenter

interface NativeAdManager {

    fun load()

    fun getNativePresenter(nativeAdGetter: NativeAdGetter?= null): NativeAdPresenter?

}