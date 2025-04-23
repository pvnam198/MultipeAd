package com.ads.applovin.rewarded.loader

import com.ads.applovin.rewarded.model.ApplovinRewardedAdConfig
import com.ads.rewarded.loader.RewardedLoader
import com.ads.rewarded.model.RewardedAdConfig
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxError
import com.applovin.mediation.MaxReward
import com.applovin.mediation.MaxRewardedAdListener
import com.applovin.mediation.ads.MaxRewardedAd

class ApplovinRewardedLoader : RewardedLoader<MaxRewardedAd> {

    override fun fetchRewardedAd(
        config: RewardedAdConfig, onSuccess: (MaxRewardedAd) -> Unit, onFailure: (String?) -> Unit
    ) {
        if (config !is ApplovinRewardedAdConfig) {
            onFailure("ApplovinRewardedAdConfig must be ApplovinRewardedAdConfig")
            return
        }

        if (!config.shouldLoad) {
            onFailure("RewardedAdConfig shouldLoad is false")
            return
        }

        val rewardedAd = MaxRewardedAd.getInstance(config.adUnitId)
        rewardedAd.setListener(object : MaxRewardedAdListener {
            override fun onUserRewarded(
                p0: MaxAd,
                p1: MaxReward
            ) {
            }

            override fun onAdLoaded(p0: MaxAd) {
                onSuccess(rewardedAd)
            }

            override fun onAdDisplayed(p0: MaxAd) {

            }

            override fun onAdHidden(p0: MaxAd) {

            }

            override fun onAdClicked(p0: MaxAd) {

            }

            override fun onAdLoadFailed(p0: String, p1: MaxError) {
                onFailure(p1.message)
            }

            override fun onAdDisplayFailed(
                p0: MaxAd,
                p1: MaxError
            ) {

            }

        })

        rewardedAd.loadAd()
    }

}