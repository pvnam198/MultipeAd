package com.ads.open.manager

import com.ads.model.AdNetworkType
import com.ads.open.loader.OpenAdConfig
import com.ads.open.presenter.OpenAdPresenter
import com.ads.open.presenter.OpenAdPresenterConfig

class OpenAdManagerImpl(
    private val adNetworkConfigs: List<Pair<AdNetworkType, OpenAdConfig>>
) : OpenAdManager {

    private val openAdPresenter: OpenAdPresenter? = null

    override fun show(configs: List<OpenAdPresenterConfig>, onComplete: () -> Unit) {
        val openAdPresenter = this.openAdPresenter ?: run {
            onComplete()
            return
        }

        configs.forEach {
            if (openAdPresenter.canShow(it)) {
                openAdPresenter.show(it, onComplete = {
                    onComplete()
                })
                return
            }
        }

    }

    override fun load() {
        if (openAdPresenter != null) return


    }
}