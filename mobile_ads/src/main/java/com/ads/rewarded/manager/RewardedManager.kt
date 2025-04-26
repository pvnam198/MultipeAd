package com.ads.rewarded.manager

import com.ads.rewarded.presenter.RewardedAdResponse
import com.ads.rewarded.presenter.RewardedPresenterConfig

interface RewardedManager {

    fun show(configs: List<RewardedPresenterConfig>, responseCallback: (RewardedAdResponse) -> Unit)

    fun load()

}