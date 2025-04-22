package com.ads.applovin.nativead.presenter

import android.content.Context
import com.ads.nativead.presenter.NativeAdPresenterConfig

data class MaxNativeAdPresenterConfig(
    val context: Context,
    val shouldShow: Boolean,
    val adContainer: CustomMaxNativeView
) : NativeAdPresenterConfig