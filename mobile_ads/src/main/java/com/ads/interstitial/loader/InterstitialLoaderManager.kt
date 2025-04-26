package com.ads.interstitial.loader

import com.ads.interstitial.presenter.InterstitialAdPresenter
import com.ads.model.AdFailure

interface InterstitialLoaderManager {
    fun load(
        onComplete: (InterstitialAdPresenter) -> Unit,
        onFailure: (List<AdFailure>) -> Unit
    )
}