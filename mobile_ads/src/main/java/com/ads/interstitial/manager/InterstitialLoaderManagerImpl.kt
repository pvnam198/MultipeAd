package com.ads.interstitial.manager

import com.ads.admob.interstitial.loader.AdmobInterstitialLoader
import com.ads.admob.interstitial.presenter.AdmobInterstitialAdPresenter
import com.ads.applovin.interstitial.loader.ApplovinInterstitialLoader
import com.ads.applovin.presenter.ApplovinInterstitialAdPresenter
import com.ads.interstitial.model.InterstitialAdConfig
import com.ads.interstitial.model.InterstitialFailure
import com.ads.interstitial.presenter.InterstitialAdPresenter
import com.ads.model.AdNetworkType

class InterstitialLoaderManagerImpl(
    private val adNetworkConfigs: List<Pair<AdNetworkType, InterstitialAdConfig>>
) : InterstitialLoaderManager {

    override fun load(
        onComplete: (InterstitialAdPresenter) -> Unit,
        onFailure: (List<InterstitialFailure>) -> Unit
    ) {
        val iterator = adNetworkConfigs.iterator()
        val failures = mutableListOf<InterstitialFailure>()
        tryNextAdNetwork(iterator, onComplete = {
            onComplete(it)
        }, onFailure = {
            failures.add(it)
        })
        if (failures.isNotEmpty()) {
            onFailure(failures)
        }
    }

    private fun tryNextAdNetwork(
        iterator: Iterator<Pair<AdNetworkType, InterstitialAdConfig>>,
        onComplete: (InterstitialAdPresenter) -> Unit,
        onFailure: (InterstitialFailure) -> Unit
    ) {
        if (!iterator.hasNext()) {
            return
        }

        val (adNetwork, config) = iterator.next()
        when (adNetwork) {
            AdNetworkType.ADMOB -> {
                loadAdmobInterstitial(config, onComplete) {
                    onFailure(InterstitialFailure(adNetwork, it))
                    tryNextAdNetwork(iterator, onComplete, onFailure)
                }
            }

            AdNetworkType.APPLOVIN -> {
                loadApplovinInterstitial(config, onComplete) {
                    onFailure(InterstitialFailure(adNetwork, it))
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