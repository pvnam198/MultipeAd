package com.ads.interstitial.presenter

interface InterstitialAdPresenter {

    fun canShow(config: InterstitialPresenterConfig): Boolean

    fun show(config: InterstitialPresenterConfig, onComplete: () -> Unit)

}