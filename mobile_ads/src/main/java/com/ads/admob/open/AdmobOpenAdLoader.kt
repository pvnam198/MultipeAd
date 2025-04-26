package com.ads.admob.open

import com.ads.open.loader.OpenAdConfig
import com.ads.open.loader.OpenAdLoader
import com.ads.open.presenter.OpenAdPresenter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback

class AdmobOpenAdLoader : OpenAdLoader {

    override fun load(
        adConfig: OpenAdConfig,
        onSuccess: (OpenAdPresenter) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        if (adConfig !is AdmobOpenAdConfig) {
            onFailure("OpenAdConfig must be AdmobOpenAdConfig")
            return
        }

        val request = AdRequest.Builder().build()
        AppOpenAd.load(
            adConfig.context, adConfig.adUnitId, request,
            object : AppOpenAdLoadCallback() {

                override fun onAdLoaded(ad: AppOpenAd) {
                    onSuccess(AdmobOpenAdPresenter(ad))
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    onFailure(loadAdError.message)
                }
            })
    }
}