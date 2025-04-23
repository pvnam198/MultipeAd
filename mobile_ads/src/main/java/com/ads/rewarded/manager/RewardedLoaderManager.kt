package com.ads.rewarded.manager

import com.ads.interstitial.model.InterstitialFailure
import com.ads.interstitial.presenter.InterstitialAdPresenter
import com.ads.rewarded.model.RewardedFailure
import com.ads.rewarded.presenter.RewardedAdPresenter

interface RewardedLoaderManager {
    fun load(
        onComplete: (RewardedAdPresenter) -> Unit,
        onFailure: (List<RewardedFailure>) -> Unit
    )
}