package com.ads.applovin.rewarded.model

import com.ads.rewarded.model.RewardedAdConfig

class ApplovinRewardedAdConfig(
    override val adUnitId: String,
    val shouldLoad: Boolean
) : RewardedAdConfig