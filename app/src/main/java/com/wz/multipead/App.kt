package com.wz.multipead

import android.app.Application
import com.ads.initializer.AdInitializationManagerImpl
import com.ads.initializer.AdInitializer
import com.ads.admob.AdMobAdInitializer
import com.ads.applovin.ApplovinAdInitializer

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
        val testDeviceIds = listOf("7ce2ea31-04ca-4dbf-93b0-bc98e690e3b4")

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