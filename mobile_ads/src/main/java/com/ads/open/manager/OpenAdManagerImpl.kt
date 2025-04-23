package com.ads.open.manager

import android.util.Log
import com.ads.model.AdFailure
import com.ads.model.AdNetworkType
import com.ads.open.loader.OpenAdConfig
import com.ads.open.loader.OpenAdLoaderManager
import com.ads.open.loader.OpenAdLoaderManagerImpl
import com.ads.open.presenter.OpenAdPresenter
import com.ads.open.presenter.OpenAdPresenterConfig

class OpenAdManagerImpl(
    private val adNetworkConfigs: List<Pair<AdNetworkType, OpenAdConfig>>
) : OpenAdManager {

    private var isLoading: Boolean = false

    private var openAdPresenter: OpenAdPresenter? = null

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
        if (isLoading) return
        isLoading = true
        val loaderManager: OpenAdLoaderManager = OpenAdLoaderManagerImpl(adNetworkConfigs)
        loaderManager.load(onComplete = {
            this.openAdPresenter = it
            isLoading = false
        }, onFailure = { failures ->
            logOpenAdFailures(failures)
            this.openAdPresenter = null
            isLoading = false
        })
    }

    private fun logOpenAdFailures(failures: List<AdFailure>) {
        failures.forEach { failure ->
            Log.e(
                "fata_open_ad", """
            ❌ Open Ad Load Failure
            ➤ Ad Network : ${failure.adNetworkType}
            ➤ Message    : ${failure.message.orEmpty()}
        """.trimIndent()
            )
        }
    }

}