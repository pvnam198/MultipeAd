package com.ads.open.presenter

interface OpenAdPresenter {

    fun canShow(config: OpenAdPresenterConfig): Boolean

    fun show(config: OpenAdPresenterConfig, onComplete: () -> Unit)

}