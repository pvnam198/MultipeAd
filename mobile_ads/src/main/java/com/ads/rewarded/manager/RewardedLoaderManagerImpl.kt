package com.ads.rewarded.manager

import com.ads.admob.rewarded.loader.AdmobRewardedLoader
import com.ads.admob.rewarded.presenter.AdmobRewardedAdPresenter
import com.ads.applovin.rewarded.loader.ApplovinRewardedLoader
import com.ads.applovin.rewarded.presenter.ApplovinRewardedAdPresenter
import com.ads.model.AdNetworkType
import com.ads.rewarded.model.RewardedAdConfig
import com.ads.rewarded.model.RewardedFailure
import com.ads.rewarded.presenter.RewardedAdPresenter

class RewardedLoaderManagerImpl(
    private val adNetworkConfigs: List<Pair<AdNetworkType, RewardedAdConfig>>
) : RewardedLoaderManager {

    override fun load(
        onComplete: (RewardedAdPresenter) -> Unit, onFailure: (List<RewardedFailure>) -> Unit
    ) {
        val iterator = adNetworkConfigs.iterator()
        val failures = mutableListOf<RewardedFailure>()
        tryNextAdNetwork(iterator, onComplete = { rewardedAdPresenter ->
            if (rewardedAdPresenter != null) {
                onComplete(rewardedAdPresenter)
            } else {
                onFailure(failures)
            }
        }, onFailure = {
            failures.add(it)
        })
    }

    private fun tryNextAdNetwork(
        iterator: Iterator<Pair<AdNetworkType, RewardedAdConfig>>,
        onComplete: (RewardedAdPresenter?) -> Unit,
        onFailure: (RewardedFailure) -> Unit,
    ) {
        if (!iterator.hasNext()) {
            onComplete(null)
            return
        }

        val (adNetwork, config) = iterator.next()
        when (adNetwork) {
            AdNetworkType.ADMOB -> {
                loadAdmobRewarded(config, onComplete) {
                    onFailure(RewardedFailure(adNetwork, it))
                    tryNextAdNetwork(iterator, onComplete, onFailure)
                }
            }

            AdNetworkType.APPLOVIN -> {
                loadApplovinRewarded(config, onComplete) {
                    onFailure(RewardedFailure(adNetwork, it))
                    tryNextAdNetwork(iterator, onComplete, onFailure)
                }
            }
        }
    }

    private fun loadAdmobRewarded(
        config: RewardedAdConfig,
        onComplete: (RewardedAdPresenter) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        AdmobRewardedLoader().fetchRewardedAd(config, onSuccess = { rewardedAd ->
            val presenter = AdmobRewardedAdPresenter(rewardedAd)
            onComplete(presenter)
        }, onFailure)
    }

    private fun loadApplovinRewarded(
        config: RewardedAdConfig,
        onComplete: (RewardedAdPresenter) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        ApplovinRewardedLoader().fetchRewardedAd(config, onSuccess = { rewardedAd ->
            val presenter = ApplovinRewardedAdPresenter(rewardedAd)
            onComplete(presenter)
        }, onFailure)
    }

}