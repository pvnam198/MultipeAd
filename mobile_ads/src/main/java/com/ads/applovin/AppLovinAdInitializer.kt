package com.ads.applovin

import android.content.Context
import com.ads.AdInitializer
import com.applovin.sdk.AppLovinMediationProvider
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkInitializationConfiguration

class AppLovinAdInitializer(
    private val context: Context,
    private val sdkKey: String,
    private val testDeviceIds: List<String> = emptyList(),
    private val onInitializeComplete: (() -> Unit)? = null
) : AdInitializer {

    override fun initialize() {
        val initConfig =
            AppLovinSdkInitializationConfiguration.builder(sdkKey)
                .setMediationProvider(AppLovinMediationProvider.MAX)
                .setTestDeviceAdvertisingIds(testDeviceIds)
                .build()

        AppLovinSdk.getInstance(context).initialize(initConfig) { sdkConfig ->
            onInitializeComplete?.invoke()
        }
    }
}