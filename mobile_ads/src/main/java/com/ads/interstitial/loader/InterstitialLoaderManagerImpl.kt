package com.ads.interstitial.loader

import com.ads.admob.interstitial.loader.AdmobInterstitialLoader
import com.ads.admob.interstitial.presenter.AdmobInterstitialAdPresenter
import com.ads.applovin.interstitial.loader.ApplovinInterstitialLoader
import com.ads.applovin.interstitial.presenter.ApplovinInterstitialAdPresenter
import com.ads.interstitial.loader.InterstitialLoaderManager
import com.ads.interstitial.model.InterstitialAdConfig
import com.ads.interstitial.presenter.InterstitialAdPresenter
import com.ads.model.AdFailure
import com.ads.model.AdNetworkType

class InterstitialLoaderManagerImpl(
    private val adNetworkConfigs: List<Pair<AdNetworkType, InterstitialAdConfig>>
) : InterstitialLoaderManager {

    override fun load(
        onComplete: (InterstitialAdPresenter) -> Unit,
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
        iterator: Iterator<Pair<AdNetworkType, InterstitialAdConfig>>,
        onComplete: (InterstitialAdPresenter?) -> Unit,
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
        config: InterstitialAdConfig,
        onComplete: (InterstitialAdPresenter) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        AdmobInterstitialLoader().fetchInterstitialAd(config, onSuccess = { interstitialAd ->
            val presenter = AdmobInterstitialAdPresenter(interstitialAd)
            onComplete(presenter)
        }, onFailure)
    }

    private fun loadApplovinInterstitial(
        config: InterstitialAdConfig,
        onComplete: (InterstitialAdPresenter) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        ApplovinInterstitialLoader().fetchInterstitialAd(config, onSuccess = { interstitialAd ->
            val presenter = ApplovinInterstitialAdPresenter(interstitialAd)
            onComplete(presenter)
        }, onFailure)
    }

}