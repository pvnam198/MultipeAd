package com.ads.applovin.nativead.presenter

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import com.ads.nativead.presenter.CustomNativeView
import com.applovin.mediation.nativeAds.MaxNativeAdView
import com.applovin.mediation.nativeAds.MaxNativeAdViewBinder
import com.lib.mobileads.R

class CustomMaxNativeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), CustomNativeView {

    private var _maxNativeAdView: MaxNativeAdView? = null
    val maxNativeAdView: MaxNativeAdView? get() = _maxNativeAdView

    fun setAdLayout(@LayoutRes layoutId: Int) {
        removeAllViews()
        val nativeAdView = createNativeAdView(context, layoutId)
        _maxNativeAdView = nativeAdView
        addView(nativeAdView)
    }

    private fun createNativeAdView(context: Context, @LayoutRes layoutId: Int): MaxNativeAdView {
        val binder: MaxNativeAdViewBinder =
            MaxNativeAdViewBinder.Builder(layoutId).setTitleTextViewId(R.id.title_text_view)
                .setBodyTextViewId(R.id.body_text_view)
                .setAdvertiserTextViewId(R.id.advertiser_text_view)
                .setIconImageViewId(R.id.icon_image_view)
                .setMediaContentViewGroupId(R.id.media_view_container)
                .setOptionsContentViewGroupId(R.id.options_view)
                .setStarRatingContentViewGroupId(R.id.star_rating_view)
                .setCallToActionButtonId(R.id.cta_button).build()
        return MaxNativeAdView(binder, context)
    }

}