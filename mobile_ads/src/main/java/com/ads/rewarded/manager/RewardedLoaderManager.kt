package com.ads.rewarded.manager

import com.ads.rewarded.model.RewardedFailure
import com.ads.rewarded.presenter.RewardedAdPresenter

interface RewardedLoaderManager {
    fun load(
        onComplete: (RewardedAdPresenter) -> Unit,
        onFailure: (List<RewardedFailure>) -> Unit
    )
}