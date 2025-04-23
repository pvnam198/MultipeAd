package com.ads.applovin.open

import com.ads.open.presenter.OpenAdPresenterConfig
import com.ads.open.presenter.OpenAdPresenterListener

class ApplovinOpenAdPresenterConfig(
    val openAdPresenterListener: OpenAdPresenterListener
) : OpenAdPresenterConfig {
    override fun onShowAdComplete(msg: String?) {
        openAdPresenterListener.onShowAdComplete(msg)
    }
}