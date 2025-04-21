package com.ads.admob.nativead.presenter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import com.ads.nativead.presenter.CustomNativeView
import com.google.android.gms.ads.nativead.NativeAdView

class CustomAdmobNativeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), CustomNativeView {

    private val nativeAdView = NativeAdView(context)

    init {
        removeAllViews()
        addView(nativeAdView)
    }

    fun setAdLayout(@LayoutRes layoutId: Int) {
        nativeAdView.removeAllViews()
        LayoutInflater.from(context).inflate(layoutId, nativeAdView, true)
    }

    fun getNativeAdView(): NativeAdView = nativeAdView
}