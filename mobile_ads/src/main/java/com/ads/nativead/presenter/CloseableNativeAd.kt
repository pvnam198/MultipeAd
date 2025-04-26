package com.ads.nativead.presenter

import com.ads.nativead.listener.NativeAdCloseListener

interface CloseableNativeAd {

    var adCloseListener: NativeAdCloseListener?

    fun onAdClosed()

}