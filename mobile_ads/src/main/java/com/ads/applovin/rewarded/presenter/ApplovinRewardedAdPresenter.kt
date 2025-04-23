package com.ads.applovin.rewarded.presenter

import android.util.Log
import com.ads.rewarded.presenter.RewardedAdFailed
import com.ads.rewarded.presenter.RewardedAdPresenter
import com.ads.rewarded.presenter.RewardedAdResponse
import com.ads.rewarded.presenter.RewardedPresenterConfig
import com.ads.rewarded.presenter.UserEarnedReward
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxError
import com.applovin.mediation.MaxReward
import com.applovin.mediation.MaxRewardedAdListener
import com.applovin.mediation.ads.MaxRewardedAd

class ApplovinRewardedAdPresenter(
    private val maxRewardedAd: MaxRewardedAd
) : RewardedAdPresenter {

    override fun canShow(config: RewardedPresenterConfig): Boolean {
        return config is ApplovinRewardedPresenterConfig
    }

    override fun show(config: RewardedPresenterConfig, onResponse: (RewardedAdResponse) -> Unit) {
        if (config !is ApplovinRewardedPresenterConfig) return onResponse(RewardedAdFailed("RewardedPresenterConfig must be ApplovinRewardedPresenterConfig"))
        if (!config.shouldShow) return onResponse(RewardedAdFailed("Should show is false"))

        maxRewardedAd.setListener(object : MaxRewardedAdListener {
            override fun onUserRewarded(
                p0: MaxAd, p1: MaxReward
            ) {
                onResponse(UserEarnedReward())
            }

            override fun onAdLoaded(p0: MaxAd) {
                Log.d("ApplovinRewardedAdPresenter", "onAdLoaded: ")
            }

            override fun onAdDisplayed(p0: MaxAd) {
                Log.d("ApplovinRewardedAdPresenter", "onAdDisplayed: ")
            }

            override fun onAdHidden(p0: MaxAd) {
                Log.d("ApplovinRewardedAdPresenter", "onAdHidden: ")
            }

            override fun onAdClicked(p0: MaxAd) {
                Log.d("ApplovinRewardedAdPresenter", "onAdClicked: ")
            }

            override fun onAdLoadFailed(p0: String, p1: MaxError) {
                onResponse(RewardedAdFailed(p1.message))
            }

            override fun onAdDisplayFailed(
                p0: MaxAd, p1: MaxError
            ) {
                onResponse(RewardedAdFailed(p1.message))
            }

        })
        maxRewardedAd.showAd(config.activity)
    }
}