package com.ads.rewarded.manager

import com.ads.interstitial.presenter.InterstitialPresenterConfig
import com.ads.rewarded.model.RewardedAdConfig
import com.ads.rewarded.presenter.RewardedAdResponse
import com.ads.rewarded.presenter.RewardedPresenterConfig

interface RewardedManager {

    fun show(configs: List<RewardedPresenterConfig>, onComplete: (RewardedAdResponse) -> Unit)

    fun load()

}