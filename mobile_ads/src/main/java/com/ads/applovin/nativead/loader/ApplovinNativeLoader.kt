package com.ads.applovin.nativead.loader

import com.ads.nativead.loader.NativeAdLoader
import com.ads.nativead.model.NativeConfig
import com.applovin.mediation.nativeAds.MaxNativeAd

class ApplovinNativeLoader: NativeAdLoader<MaxNativeAd> {


    override fun load(
        config: NativeConfig,
        onSuccess: (MaxNativeAd) -> Unit,
        onFailure: (String?) -> Unit
    ) {

    }
}