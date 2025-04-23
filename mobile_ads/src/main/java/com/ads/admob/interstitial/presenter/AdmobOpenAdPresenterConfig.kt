package com.ads.admob.interstitial.presenter

import android.app.Activity
import com.ads.open.presenter.OpenAdPresenterConfig
import com.ads.open.presenter.OpenAdPresenterListener

class AdmobOpenAdPresenterConfig(
    val activity: Activity,
    val openAdPresenterListener: OpenAdPresenterListener
) : OpenAdPresenterConfig {
    override fun onShowAdComplete() {
        openAdPresenterListener.onShowAdComplete()
    }
}