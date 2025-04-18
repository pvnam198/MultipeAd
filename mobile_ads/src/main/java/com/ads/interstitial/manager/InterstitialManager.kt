package com.ads.interstitial.manager

import com.ads.interstitial.presenter.InterstitialPresenterConfig

interface InterstitialManager {

    fun show(configs: List<InterstitialPresenterConfig>, onComplete: () -> Unit)

    fun load()

}