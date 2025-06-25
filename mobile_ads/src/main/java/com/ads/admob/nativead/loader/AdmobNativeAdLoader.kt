package com.ads.admob.nativead.loader

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.ads.admob.nativead.AdLoadFailureDelay
import com.ads.admob.nativead.model.AdmobNativeConfig
import com.ads.admob.nativead.presenter.AdmobNativeAdPresenter
import com.ads.nativead.loader.NativeAdLoader
import com.ads.nativead.model.NativeConfig
import com.ads.nativead.presenter.NativeAdPresenter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions

class AdmobNativeAdLoader(
    private val loadedAds: Collection<NativeAdPresenter>,
) : NativeAdLoader<AdmobNativeAdPresenter> {
    private val failedLoadGap = AdLoadFailureDelay()
    private var adIdIndex = 0

    override fun load(
        config: NativeConfig,
        onSuccess: (AdmobNativeAdPresenter) -> Unit,
        onFailure: (String?) -> Unit
    ) {

        if (config !is AdmobNativeConfig) {
            onFailure("NativeConfig must be AdmobNativeConfig")
            return
        }

        val adUnitIds = config.adUnitId
        if (adUnitIds.isEmpty()) {
            onFailure("AdMob adUnitId list is empty")
            return
        }

        val maxLoadNative = config.maxLoadNative

        fun getNextAdUnitId(): String {
            val adUnitId = adUnitIds[adIdIndex]
            Log.d("Log_native", "adUnitId: $adIdIndex")
            adIdIndex = (adIdIndex + 1) % adUnitIds.size
            return adUnitId
        }

        fun tryLoad() {
            Log.d("Log_native", "listNative: $loadedAds    -- max : $maxLoadNative")
            if (loadedAds.size >= maxLoadNative) {
                return
            }
            val nativeAdId = getNextAdUnitId()
            Log.d("Log_native", "id Native: $nativeAdId")

            var presenter: AdmobNativeAdPresenter? = null

            val adLoader = AdLoader.Builder(config.context, nativeAdId)
                .forNativeAd { ad: NativeAd ->
                    Log.d("Log_native", "native: $ad --- ID $nativeAdId")
                    failedLoadGap.resetFailureCount()
                    presenter = AdmobNativeAdPresenter(ad, nativeAdId)
                    onSuccess(presenter)
                    tryLoad()
                }

                .withAdListener(object : AdListener() {
                    override fun onAdFailedToLoad(error: LoadAdError) {
                        super.onAdFailedToLoad(error)
                        Log.d("Log_native", "native loader: ${error.message} --- ID $nativeAdId")
                        val isLastId = adUnitIds.indexOf(nativeAdId) == adUnitIds.lastIndex
                        if (isLastId) {
                            failedLoadGap.recordFailedLoad()
                            Handler(Looper.getMainLooper()).postDelayed({
                                tryLoad()
                            }, failedLoadGap.getFailureTime())
                        } else {
                            tryLoad()
                        }
                    }

                    override fun onAdClosed() {
                        super.onAdClosed()
                        presenter?.onAdClosed()
                    }

                })
                .withNativeAdOptions(NativeAdOptions.Builder().build())
                .build()

            adLoader.loadAd(AdRequest.Builder().build())
        }

        tryLoad()
    }
}