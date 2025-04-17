package com.wz.multipead

import android.app.Application
import com.ads.AdInitializationManagerImpl
import com.ads.AdInitializer
import com.ads.admob.AdMobAdInitializer
import com.ads.applovin.AppLovinAdInitializer
import com.ads.config.ConfigReader
import com.ads.config.ConfigReaderImpl

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeAds()
    }

    private fun initializeAds() {
        val configReader: ConfigReader = ConfigReaderImpl(this)
        val initializers = ArrayList<AdInitializer>()
        getMaxAdInitializer(configReader)?.let { initializers.add(it) }

        val adMobAdInitializer = AdMobAdInitializer(context = this)
        initializers.add(adMobAdInitializer)

        val adInitializationManager =
            AdInitializationManagerImpl(initializers)
        adInitializationManager.initialize()
    }

    private fun getMaxAdInitializer(configReader: ConfigReader): AdInitializer? {
        val sdkKey = configReader.readProperty("applovin.sdk.key") ?: return null

        val testDeviceIds = configReader.readProperty("applovin.test.device.ids")
            ?.split(",")
            ?.map { it.trim() }
            .orEmpty()

        return AppLovinAdInitializer(
            context = this,
            sdkKey = sdkKey,
            testDeviceIds = testDeviceIds
        )
    }

}