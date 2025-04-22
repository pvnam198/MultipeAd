package com.ads.nativead.manager

import com.ads.admob.nativead.loader.AdmobNativeAdLoader
import com.ads.applovin.nativead.loader.ApplovinNativeLoader
import com.ads.model.AdNetworkType
import com.ads.nativead.model.NativeConfig
import com.ads.nativead.presenter.NativeAdPresenter

class NativeAdManagerImpl(
    private val nativeAdConfigs: List<Pair<AdNetworkType, NativeConfig>>
) : NativeAdManager {

    private val nativeAdPresenters = ArrayDeque<NativeAdPresenter>()

    override fun load() {
        val iterator = nativeAdConfigs.iterator()
        tryNextAdNetwork(iterator = iterator, onComplete = { adPresenter ->
            if (adPresenter == null) {
                return@tryNextAdNetwork
            }
            nativeAdPresenters.add(adPresenter)
        })
    }

    override fun getNativePresenter(): NativeAdPresenter? {
        val nativeAdPresenter = nativeAdPresenters.removeFirstOrNull()
        load()
        return nativeAdPresenter
    }

    private fun tryNextAdNetwork(
        iterator: Iterator<Pair<AdNetworkType, NativeConfig>>,
        onComplete: (NativeAdPresenter?) -> Unit
    ) {
        if (!iterator.hasNext()) {
            onComplete(null)
            return
        }

        val (adNetwork, config) = iterator.next()

        when (adNetwork) {
            AdNetworkType.ADMOB -> {
                AdmobNativeAdLoader().load(config = config, onSuccess = {
                    onComplete(it)
                }, onFailure = {
                    tryNextAdNetwork(iterator, onComplete)
                })
            }

            AdNetworkType.APPLOVIN -> {
                ApplovinNativeLoader().load(config = config, onSuccess = {
                    onComplete(it)
                }, onFailure = {
                    tryNextAdNetwork(iterator, onComplete)
                })
            }
        }
    }

}