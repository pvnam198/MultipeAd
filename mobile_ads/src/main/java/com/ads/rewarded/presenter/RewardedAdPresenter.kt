package com.ads.rewarded.presenter

interface RewardedAdPresenter {

    fun canShow(config: RewardedPresenterConfig): Boolean

    fun show(config: RewardedPresenterConfig, onResponse: (RewardedAdResponse) -> Unit)

}