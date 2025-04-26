package com.ads.rewarded.manager

import android.util.Log
import com.ads.model.AdNetworkType
import com.ads.rewarded.model.RewardedAdConfig
import com.ads.rewarded.model.RewardedFailure
import com.ads.rewarded.presenter.RewardedAdFailed
import com.ads.rewarded.presenter.RewardedAdPresenter
import com.ads.rewarded.presenter.RewardedAdResponse
import com.ads.rewarded.presenter.RewardedPresenterConfig

class RewardedManagerImpl(
    private val adNetworkConfigs: List<Pair<AdNetworkType, RewardedAdConfig>>
) : RewardedManager {

    private var rewardedAdPresenter: RewardedAdPresenter? = null

    override fun show(
        configs: List<RewardedPresenterConfig>,
        responseCallback: (RewardedAdResponse) -> Unit
    ) {

        val rewardedAd =
            rewardedAdPresenter
                ?: return responseCallback(RewardedAdFailed("Rewarded Ad Not Found"))
        rewardedAdPresenter = null

        configs.forEach {
            if (rewardedAd.canShow(it)) {
                rewardedAd.show(it, onResponse = responseCallback)
                return
            }
        }
    }

    override fun load() {
        if (rewardedAdPresenter != null) return

        val loaderManager: RewardedLoaderManager =
            RewardedLoaderManagerImpl(adNetworkConfigs)
        loaderManager.load(
            onComplete = {
                Log.d("fata_rewarded", "load: $it")
                rewardedAdPresenter = it
            },
            onFailure = { failures ->
                logInterstitialFailures(failures)
            }
        )
    }

    private fun logInterstitialFailures(failures: List<RewardedFailure>) {
        failures.forEach { failure ->
            Log.e(
                "fata_rewarded", """
            ❌ Rewarded Ad Load Failure
            ➤ Ad Network : ${failure.adNetworkType}
            ➤ Message    : ${failure.message.orEmpty()}
        """.trimIndent()
            )
        }
    }

}