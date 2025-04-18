package com.ads.interstitial.manager

import com.ads.interstitial.model.InterstitialFailure
import com.ads.interstitial.presenter.InterstitialAdPresenter

interface InterstitialLoaderManager {
    fun load(
        onComplete: (InterstitialAdPresenter) -> Unit,
        onFailure: (List<InterstitialFailure>) -> Unit
    )
}