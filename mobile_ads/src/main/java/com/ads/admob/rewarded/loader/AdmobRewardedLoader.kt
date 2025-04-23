package com.ads.admob.rewarded.loader

import com.ads.admob.rewarded.model.AdmobRewardedAdConfig
import com.ads.rewarded.loader.RewardedLoader
import com.ads.rewarded.model.RewardedAdConfig
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class AdmobRewardedLoader : RewardedLoader<RewardedAd> {
    override fun fetchRewardedAd(
        config: RewardedAdConfig, onSuccess: (RewardedAd) -> Unit, onFailure: (String?) -> Unit
    ) {

        if (config !is AdmobRewardedAdConfig) {
            onFailure("RewardedAdConfig must be AdmobRewardedAdConfig")
            return
        }

        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            config.context, config.adUnitId, adRequest, object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    onFailure.invoke(p0.message)
                }

                override fun onAdLoaded(p0: RewardedAd) {
                    super.onAdLoaded(p0)
                    onSuccess.invoke(p0)
                }
            })
    }
}