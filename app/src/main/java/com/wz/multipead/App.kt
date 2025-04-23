package com.wz.multipead

import android.app.Application
import com.ads.initializer.AdInitializationManagerImpl
import com.ads.initializer.AdInitializer
import com.ads.admob.AdMobAdInitializer
import com.ads.applovin.ApplovinAdInitializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class App : Application() {

    var applovinAdInitializer: ApplovinAdInitializer?= null

    override fun onCreate() {
        super.onCreate()
        initializeAds()
    }

    private fun initializeAds() {
        val initializers = ArrayList<AdInitializer>()
        getMaxAdInitializer()?.let { initializers.add(it) }

        getAdmobInitializer()?.let {
            initializers.add(it)
        }

        val adInitializationManager =
            AdInitializationManagerImpl(initializers)
        adInitializationManager.initialize()
    }

    private fun getMaxAdInitializer(): AdInitializer? {
        val sdkKey =
            "sJ15ca4POpBC2JHIEOf7xoye1fy55OxDDPQtcP2ced81fcJLVtTWhF3kT8vthO6xImtr946dol4twMiIkPmsrU"
        val testDeviceIds = listOf("17989b26-3f24-4db2-a176-abb5a51e2ecc")

        return ApplovinAdInitializer(
            context = this,
            sdkKey = sdkKey,
            testDeviceIds = testDeviceIds
        ).apply { this@App.applovinAdInitializer = this }
    }

    private fun getAdmobInitializer(): AdInitializer? {
        return AdMobAdInitializer(context = this)
    }

}