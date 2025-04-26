package com.ads.open.manager

import com.ads.open.presenter.OpenAdPresenterConfig

interface OpenAdManager {

    fun show(configs: List<OpenAdPresenterConfig>, onComplete: () -> Unit)

    fun load()

}