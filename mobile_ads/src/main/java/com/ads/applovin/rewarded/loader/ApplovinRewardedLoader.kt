package com.ads.applovin.rewarded.loader

import android.util.Log
import com.ads.applovin.rewarded.model.ApplovinRewardedAdConfig
import com.ads.applovin.rewarded.presenter.ApplovinRewardedAdPresenter
import com.ads.rewarded.loader.RewardedLoader
import com.ads.rewarded.model.RewardedAdConfig
import com.ads.rewarded.presenter.RewardedAdCompleted
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxError
import com.applovin.mediation.MaxReward
import com.applovin.mediation.MaxRewardedAdListener
import com.applovin.mediation.ads.MaxRewardedAd

class ApplovinRewardedLoader : RewardedLoader<ApplovinRewardedAdPresenter> {

    override fun fetchRewardedAd(
        config: RewardedAdConfig, onSuccess: (ApplovinRewardedAdPresenter) -> Unit, onFailure: (String?) -> Unit
    ) {
        if (config !is ApplovinRewardedAdConfig) {
            onFailure("ApplovinRewardedAdConfig must be ApplovinRewardedAdConfig")
            return
        }

        if (!config.shouldLoad) {
            onFailure("RewardedAdConfig shouldLoad is false")
            return
        }

        var applovinRewardedAdPresenter: ApplovinRewardedAdPresenter?= null
        val rewardedAd = MaxRewardedAd.getInstance(config.adUnitId)
        var isUserRewarded = false

        rewardedAd.setListener(object : MaxRewardedAdListener {
            override fun onUserRewarded(
                p0: MaxAd,
                p1: MaxReward
            ) {
                isUserRewarded = true
                applovinRewardedAdPresenter?.onUserRewarded()
                Log.d("log_test_reward", "onUserRewarded: ")
            }

            override fun onAdLoaded(p0: MaxAd) {
                applovinRewardedAdPresenter = ApplovinRewardedAdPresenter(rewardedAd)
                onSuccess(applovinRewardedAdPresenter)
            }

            override fun onAdDisplayed(p0: MaxAd) {
            }

            override fun onAdHidden(p0: MaxAd) {
                Log.d("log_test_reward", "onAdHidden applovinRewardedAdPresenter: $applovinRewardedAdPresenter")
                applovinRewardedAdPresenter?.onRewardCompleted(RewardedAdCompleted(isUserRewarded))
            }

            override fun onAdClicked(p0: MaxAd) {}

            override fun onAdLoadFailed(p0: String, p1: MaxError) {
                onFailure(p1.message)
            }

            override fun onAdDisplayFailed(
                p0: MaxAd,
                p1: MaxError
            ) {
                Log.d("log_test_reward", "onAdDisplayFailed applovinRewardedAdPresenter: $applovinRewardedAdPresenter")
                applovinRewardedAdPresenter?.onRewardCompleted(RewardedAdCompleted(isUserRewarded))
            }

        })

        rewardedAd.loadAd()
    }

}