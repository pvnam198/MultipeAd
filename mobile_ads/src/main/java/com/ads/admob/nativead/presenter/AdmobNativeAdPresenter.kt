package com.ads.admob.nativead.presenter

import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.ads.model.AdUnitIdProvider
import com.ads.nativead.listener.NativeAdCloseListener
import com.ads.nativead.model.DisplayableNativeAd
import com.ads.nativead.presenter.CloseableNativeAd
import com.ads.nativead.presenter.NativeAdPresenter
import com.ads.nativead.presenter.NativeAdPresenterConfig
import com.ads.open.presenter.ReleasableNativeAd
import com.google.android.gms.ads.nativead.NativeAd
import com.lib.mobileads.R

class AdmobNativeAdPresenter(
    private val nativeAd: NativeAd,
    override val adUnitId: String
) : NativeAdPresenter,
    DisplayableNativeAd,
    CloseableNativeAd,
    ReleasableNativeAd,
    AdUnitIdProvider {

    override var adCloseListener: NativeAdCloseListener? = null

    override fun show(
        config: NativeAdPresenterConfig,
        onFailure: (String?) -> Unit
    ) {
        if (config !is AdmobNativeAdPresenterConfig) {
            onFailure("AdmobNativeAdPresenterConfig must be AdmobNativeAdPresenterConfig")
            return
        }

        if (!config.shouldShow) {
            onFailure("Should not show")
            return
        }

        // adContainer là CustomAdmobNativeView → có nativeAdView bên trong
        val adContainer = config.adContainer
        val nativeAdView = adContainer.getNativeAdView()

        // Tìm các view con từ layout do app cung cấp
        nativeAdView.headlineView = nativeAdView.findViewById(R.id.ad_headline)
        nativeAdView.bodyView = nativeAdView.findViewById(R.id.ad_body)
        nativeAdView.callToActionView = nativeAdView.findViewById(R.id.ad_call_to_action)
        nativeAdView.iconView = nativeAdView.findViewById(R.id.ad_app_icon)
        nativeAdView.priceView = nativeAdView.findViewById(R.id.ad_price)
        nativeAdView.starRatingView = nativeAdView.findViewById(R.id.ad_stars)
        nativeAdView.storeView = nativeAdView.findViewById(R.id.ad_store)
        nativeAdView.advertiserView = nativeAdView.findViewById(R.id.ad_advertiser)

        // Gán dữ liệu từ nativeAd
        (nativeAdView.headlineView as? TextView)?.text = nativeAd.headline
        (nativeAdView.bodyView as? TextView)?.text = nativeAd.body
        (nativeAdView.callToActionView as? Button)?.text = nativeAd.callToAction
        (nativeAdView.iconView as? ImageView)?.setImageDrawable(nativeAd.icon?.drawable)
        (nativeAdView.priceView as? TextView)?.text = nativeAd.price
        (nativeAdView.starRatingView as? RatingBar)?.rating = nativeAd.starRating?.toFloat() ?: 0f
        (nativeAdView.storeView as? TextView)?.text = nativeAd.store
        (nativeAdView.advertiserView as? TextView)?.text = nativeAd.advertiser

        // MediaView đặc biệt
        val mediaWrapper = nativeAdView.findViewById<AdmobMediaView>(R.id.ad_media)
        mediaWrapper?.mediaView?.mediaContent = nativeAd.mediaContent
        nativeAdView.mediaView = mediaWrapper?.mediaView

        // Gắn NativeAd vào NativeAdView
        nativeAdView.setNativeAd(nativeAd)
    }

    override fun onAdClosed() {
        adCloseListener?.onAdClosed()
    }

    override fun release() {
        nativeAd.destroy()
    }
}