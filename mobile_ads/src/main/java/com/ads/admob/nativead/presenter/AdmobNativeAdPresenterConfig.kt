package com.ads.admob.nativead.presenter

import android.content.Context
import com.ads.nativead.presenter.NativeAdPresenterConfig

data class AdmobNativeAdPresenterConfig(
    val context: Context,
    val shouldShow: Boolean,
    val adContainer: CustomAdmobNativeView
) : NativeAdPresenterConfig