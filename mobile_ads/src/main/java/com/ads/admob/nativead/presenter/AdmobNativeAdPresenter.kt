package com.ads.admob.nativead.presenter

import android.util.Log
import android.widget.TextView
import com.ads.nativead.presenter.NativeAdPresenter
import com.ads.nativead.presenter.NativeAdPresenterConfig
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

class AdmobNativeAdPresenter(
    private val nativeAd: NativeAd
) : NativeAdPresenter {

    override fun show(
        config: NativeAdPresenterConfig,
        onFailure: () -> Unit
    ) {

        Log.d("log_test_123123131", "AdmobNativeAdPresenter show")

        if (config !is AdmobNativeAdPresenterConfig) {
            Log.d(
                "log_test_123123131",
                "AdmobNativeAdPresenter config !is AdmobNativeAdPresenterConfig"
            )
            onFailure()
            return
        }

        if (!config.shouldShow) {
            Log.d("log_test_123123131", "AdmobNativeAdPresenter !config.shouldShow")
            onFailure()
            return
        }
        Log.d("log_test_123123131", "AdmobNativeAdPresenter create NativeAdView")

        val textView = TextView(config.context)
        textView.text = "This is native ad"

        config.adContainer.addView(textView)


//        val adView = NativeAdView(config.context)
//        config.adHeadline?.let {
//            adView.headlineView = it
//            it.text = nativeAd.headline
//        }
//        config.adBody?.let {
//            adView.bodyView = it
//            val body = nativeAd.body
//            if (body != null) {
//                it.text = nativeAd.body
//            } else {
//
//            }
//        }
//        config.adCallToAction?.let {
//            adView.callToActionView = it
//            val callToAction = nativeAd.callToAction
//            if (callToAction != null) {
//                it.text = nativeAd.callToAction
//            } else {
//
//            }
//        }
//        config.adIcon?.let {
//            adView.iconView = it
//            val icon = nativeAd.icon
//            if (icon != null) {
//                it.setImageDrawable(icon.drawable)
//            } else {
//
//            }
//        }
//        config.adPrice?.let {
//            adView.priceView = it
//            val price = nativeAd.price
//            if (price != null) {
//                it.text = nativeAd.price
//            } else {
//
//            }
//        }
//        config.adStars?.let {
//            adView.starRatingView = it
//            val starRating = nativeAd.starRating
//            if (starRating != null) {
//                it.rating = starRating.toFloat()
//            } else {
//            }
//        }
//        config.adStore?.let {
//            adView.storeView = it
//            val store = nativeAd.store
//            if (store != null) {
//                it.text = nativeAd.store
//            } else {
//
//            }
//        }
//        config.adAdvertiser?.let {
//            adView.advertiserView = it
//            val advertiser = nativeAd.advertiser
//            if (advertiser != null) {
//                it.text = nativeAd.advertiser
//            } else {
//
//            }
//        }
//        config.adMediaView?.let {
//            adView.mediaView = it.mediaView
//            val mediaContent = nativeAd.mediaContent
//            if (mediaContent != null) {
//                it.mediaView.mediaContent = mediaContent
//            } else {
//
//            }
//        }
//        config.adContainer.addView(adView)
    }

}