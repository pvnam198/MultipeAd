package com.ads.interstitial.manager

import com.ads.model.AdFailure
import com.ads.interstitial.presenter.InterstitialAdPresenter

interface InterstitialLoaderManager {
    fun load(
        onComplete: (InterstitialAdPresenter) -> Unit,
        onFailure: (List<AdFailure>) -> Unit
    )
}