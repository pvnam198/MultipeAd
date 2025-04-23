package com.ads.open.loader

import com.ads.admob.open.AdmobOpenAdLoader
import com.ads.applovin.open.ApplovinOpenAdLoader
import com.ads.model.AdFailure
import com.ads.model.AdNetworkType
import com.ads.open.presenter.OpenAdPresenter

class OpenAdLoaderManagerImpl(
    private val adNetworkConfigs: List<Pair<AdNetworkType, OpenAdConfig>>
) : OpenAdLoaderManager {

    override fun load(
        onComplete: (OpenAdPresenter) -> Unit,
        onFailure: (List<AdFailure>) -> Unit
    ) {
        val iterator = adNetworkConfigs.iterator()
        val failures = mutableListOf<AdFailure>()
        tryNextAdNetwork(iterator, onComplete = { openAdPresenter ->
            if (openAdPresenter != null) {
                onComplete(openAdPresenter)
            } else {
                onFailure(failures)
            }
        }, onFailure = {
            failures.add(it)
        })
    }

    private fun tryNextAdNetwork(
        iterator: Iterator<Pair<AdNetworkType, OpenAdConfig>>,
        onComplete: (OpenAdPresenter?) -> Unit,
        onFailure: (AdFailure) -> Unit,
    ) {
        if (!iterator.hasNext()) {
            onComplete(null)
            return
        }

        val (adNetwork, config) = iterator.next()
        when (adNetwork) {
            AdNetworkType.ADMOB -> {
                loadAdmobOpen(config, onComplete) {
                    onFailure(AdFailure(adNetwork, it))
                    tryNextAdNetwork(iterator, onComplete, onFailure)
                }
            }

            AdNetworkType.APPLOVIN -> {
                loadApplovinOpen(config, onComplete) {
                    onFailure(AdFailure(adNetwork, it))
                    tryNextAdNetwork(iterator, onComplete, onFailure)
                }
            }
        }
    }

    private fun loadAdmobOpen(
        config: OpenAdConfig,
        onComplete: (OpenAdPresenter) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        AdmobOpenAdLoader().load(config, onSuccess = { presenter ->
            onComplete(presenter)
        }, onFailure)
    }

    private fun loadApplovinOpen(
        config: OpenAdConfig,
        onComplete: (OpenAdPresenter) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        ApplovinOpenAdLoader().load(config, onSuccess = { presenter ->
            onComplete(presenter)
        }, onFailure)
    }

}