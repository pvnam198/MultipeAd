package com.ads.applovin.rewarded.presenter

import com.ads.rewarded.presenter.RewardedAdCompleted
import com.ads.rewarded.presenter.RewardedAdFailed
import com.ads.rewarded.presenter.RewardedAdPresenter
import com.ads.rewarded.presenter.RewardedAdResponse
import com.ads.rewarded.presenter.RewardedPresenterConfig
import com.ads.rewarded.presenter.UserEarnedReward
import com.applovin.mediation.ads.MaxRewardedAd

class ApplovinRewardedAdPresenter(
    private val maxRewardedAd: MaxRewardedAd
) : RewardedAdPresenter {

    private var onResponse: ((RewardedAdResponse) -> Unit)? = null

    override fun canShow(config: RewardedPresenterConfig): Boolean {
        return config is ApplovinRewardedPresenterConfig
    }

    override fun show(config: RewardedPresenterConfig, onResponse: (RewardedAdResponse) -> Unit) {
        if (config !is ApplovinRewardedPresenterConfig) return onResponse(RewardedAdFailed("RewardedPresenterConfig must be ApplovinRewardedPresenterConfig"))
        if (!config.shouldShow) return onResponse(RewardedAdFailed("Should show is false"))
        this.onResponse = onResponse
        maxRewardedAd.showAd(config.activity)
    }

    fun onUserRewarded() {
        onResponse?.invoke(UserEarnedReward())
    }

    fun onRewardCompleted(completed: RewardedAdCompleted) {
        onResponse?.invoke(completed)
    }
}