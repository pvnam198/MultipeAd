package com.ads.applovin.interstitial.presenter

import android.app.Activity
import com.ads.interstitial.presenter.InterstitialPresenterConfig

class ApplovinInterstitialPresenterConfig(
    val activity: Activity,
    val shouldShow: Boolean
) : InterstitialPresenterConfig