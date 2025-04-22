package com.ads.nativead.loader

import com.ads.nativead.model.NativeConfig

interface NativeAdLoader<T> {

    fun load(config: NativeConfig, onSuccess: (T) -> Unit, onFailure: (String?) -> Unit)

}