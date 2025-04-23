package com.ads.rewarded.loader

import com.ads.rewarded.model.RewardedAdConfig

interface RewardedLoader<T> {

    fun fetchRewardedAd(
        config: RewardedAdConfig, onSuccess: (T) -> Unit, onFailure: (msg: String?) -> Unit
    )

}