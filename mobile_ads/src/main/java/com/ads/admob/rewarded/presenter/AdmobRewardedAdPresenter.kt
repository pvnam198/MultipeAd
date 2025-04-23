package com.ads.admob.rewarded.presenter

import com.ads.rewarded.presenter.RewardedAdCompleted
import com.ads.rewarded.presenter.RewardedAdFailed
import com.ads.rewarded.presenter.RewardedAdPresenter
import com.ads.rewarded.presenter.RewardedAdResponse
import com.ads.rewarded.presenter.RewardedPresenterConfig
import com.ads.rewarded.presenter.UserEarnedReward
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd

class AdmobRewardedAdPresenter(
    private val rewardedAd: RewardedAd
) : RewardedAdPresenter {

    override fun canShow(config: RewardedPresenterConfig): Boolean {
        return config is AdmobRewardedPresenterConfig
    }

    override fun show(config: RewardedPresenterConfig, onResponse: (RewardedAdResponse) -> Unit) {
        if (config !is AdmobRewardedPresenterConfig) return onResponse(RewardedAdFailed("RewardedPresenterConfig must be AdmobRewardedPresenterConfig"))
        if (!config.shouldShow) return onResponse(RewardedAdFailed("Should show is false"))

        rewardedAd.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
            }

            override fun onAdDismissedFullScreenContent() {
                onResponse(RewardedAdCompleted())
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                onResponse(RewardedAdFailed(adError.message))
            }

            override fun onAdImpression() {
            }

            override fun onAdShowedFullScreenContent() {
            }
        }

        rewardedAd.show(config.activity, object : OnUserEarnedRewardListener {
            override fun onUserEarnedReward(p0: RewardItem) {
                onResponse(UserEarnedReward())
            }
        })
    }
}