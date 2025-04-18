package com.ads.interstitial.manager

import android.util.Log
import com.ads.interstitial.model.InterstitialAdConfig
import com.ads.interstitial.model.InterstitialFailure
import com.ads.interstitial.presenter.InterstitialAdPresenter
import com.ads.interstitial.presenter.InterstitialPresenterConfig
import com.ads.model.AdNetworkType

class InterstitialManagerImpl(
    private val adNetworkConfigs: List<Pair<AdNetworkType, InterstitialAdConfig>>
) : InterstitialManager {

    private var interstitialAdPresenter: InterstitialAdPresenter? = null

    override fun show(
        configs: List<InterstitialPresenterConfig>,
        onComplete: () -> Unit
    ) {

        val interstitialAd = interstitialAdPresenter ?: return onComplete()

        configs.forEach {
            if (interstitialAd.canShow(it)) {
                interstitialAd.show(it, onComplete = {
                    interstitialAdPresenter = null
                    onComplete()
                })
                return
            }
        }
    }

    override fun load() {
        if (interstitialAdPresenter != null) return

        val loaderManager: InterstitialLoaderManager =
            InterstitialLoaderManagerImpl(adNetworkConfigs)
        loaderManager.load(
            onComplete = {
                interstitialAdPresenter = it
            },
            onFailure = { failures ->
                logInterstitialFailures(failures)
            }
        )
    }

    private fun logInterstitialFailures(failures: List<InterstitialFailure>) {
        failures.forEach { failure ->
            Log.e(
                "fata_interstitial", """
            ❌ Interstitial Ad Load Failure
            ➤ Ad Network : ${failure.adNetworkType}
            ➤ Message    : ${failure.message.orEmpty()}
        """.trimIndent()
            )
        }
    }

}