package com.ads.admob.nativead.presenter

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.ads.nativead.presenter.NativeAdPresenterConfig

class AdmobNativeAdPresenterConfig(
    val context: Context,
    val shouldShow: Boolean = false,
    val adContainer: ViewGroup,
    val adMediaView: AdmobMediaView? = null,
    val adHeadline: TextView? = null,
    val adBody: TextView? = null,
    val adCallToAction: TextView? = null,
    val adIcon: ImageView? = null,
    val adPrice: TextView? = null,
    val adStars: RatingBar? = null,
    val adStore: TextView? = null,
    val adAdvertiser: TextView? = null
) : NativeAdPresenterConfig