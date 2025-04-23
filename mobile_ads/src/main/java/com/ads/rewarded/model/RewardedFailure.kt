package com.ads.rewarded.model

import com.ads.model.AdNetworkType

class RewardedFailure(
    val adNetworkType: AdNetworkType,
    val message: String?
)