package com.ads.admob.interstitial.presenter

import android.app.Activity
import com.ads.interstitial.presenter.InterstitialPresenterConfig

class AdmobInterstitialPresenterConfig(
    val activity: Activity,
    val shouldShow: Boolean
) : InterstitialPresenterConfig