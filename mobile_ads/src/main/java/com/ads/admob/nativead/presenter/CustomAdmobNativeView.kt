package com.ads.admob.nativead.presenter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.ads.nativead.presenter.CustomNativeView
import com.google.android.gms.ads.nativead.NativeAdView
import com.lib.mobileads.R

class CustomAdmobNativeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), CustomNativeView {

    private val nativeAdView: NativeAdView

    init {
        val view = LayoutInflater.from(context).inflate(
            R.layout.custom_admob_native_view,
            this,
            true
        )
        nativeAdView = view as NativeAdView // Root là NativeAdView
    }

    // Trả về NativeAdView để sử dụng trong AdmobHolder
    fun getNativeAdView(): NativeAdView {
        return nativeAdView
    }
}