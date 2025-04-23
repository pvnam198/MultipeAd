package com.ads.open.manager

import com.ads.admob.open.AdmobOpenAdLoader
import com.ads.interstitial.model.InterstitialAdConfig
import com.ads.model.AdFailure
import com.ads.model.AdNetworkType
import com.ads.open.loader.OpenAdConfig
import com.ads.open.presenter.OpenAdPresenter

class OpenLoaderManagerImpl(
    private val adNetworkConfigs: List<Pair<AdNetworkType, OpenAdConfig>>
) : OpenLoaderManager {

    override fun load(
        onComplete: (OpenAdPresenter) -> Unit,
        onFailure: (List<AdFailure>) -> Unit
    ) {
        val iterator = adNetworkConfigs.iterator()
        val failures = mutableListOf<AdFailure>()
        tryNextAdNetwork(iterator, onComplete = { interstitialAdPresenter ->
            if (interstitialAdPresenter != null) {
                onComplete(interstitialAdPresenter)
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
                loadAdmobInterstitial(config, onComplete) {
                    onFailure(AdFailure(adNetwork, it))
                    tryNextAdNetwork(iterator, onComplete, onFailure)
                }
            }

            AdNetworkType.APPLOVIN -> {
                loadApplovinInterstitial(config, onComplete) {
                    onFailure(AdFailure(adNetwork, it))
                    tryNextAdNetwork(iterator, onComplete, onFailure)
                }
            }
        }
    }

    private fun loadAdmobInterstitial(
        config: OpenAdConfig,
        onComplete: (OpenAdPresenter) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        AdmobOpenAdLoader().load(config, onSuccess = { presenter ->
            onComplete(presenter)
        }, onFailure)
    }

    private fun loadApplovinInterstitial(
        config: OpenAdConfig,
        onComplete: (OpenAdPresenter) -> Unit,
        onFailure: (String?) -> Unit
    ) {
//        ApplovinInterstitialLoader().fetchInterstitialAd(config, onSuccess = { interstitialAd ->
//            val presenter = ApplovinInterstitialAdPresenter(interstitialAd)
//            onComplete(presenter)
//        }, onFailure)
    }

}