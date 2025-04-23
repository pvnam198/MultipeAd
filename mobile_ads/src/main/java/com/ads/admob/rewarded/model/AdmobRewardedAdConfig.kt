package com.ads.admob.rewarded.model

import android.content.Context
import com.ads.interstitial.model.InterstitialAdConfig
import com.ads.rewarded.model.RewardedAdConfig

class AdmobRewardedAdConfig(
    override val adUnitId: String,
    val context: Context,
    val shouldLoad: Boolean
) : RewardedAdConfig