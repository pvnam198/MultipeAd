package com.ads.applovin.rewarded.presenter

import android.app.Activity
import com.ads.interstitial.presenter.InterstitialPresenterConfig
import com.ads.rewarded.presenter.RewardedPresenterConfig

class ApplovinRewardedPresenterConfig(
    val activity: Activity,
    val shouldShow: Boolean
) : RewardedPresenterConfig