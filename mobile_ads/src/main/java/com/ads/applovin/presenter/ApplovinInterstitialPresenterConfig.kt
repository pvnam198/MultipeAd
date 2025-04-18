package com.ads.applovin.presenter

import android.app.Activity
import com.ads.interstitial.presenter.InterstitialPresenterConfig

class ApplovinInterstitialPresenterConfig(
    val activity: Activity,
    val shouldShow: Boolean
) : InterstitialPresenterConfig