package com.wz.multipead

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ads.AdNetworkType
import com.ads.banner.manager.BannerAdManager
import com.ads.banner.manager.IBannerAdManager
import com.ads.banner.model.BannerAd
import com.ads.banner.model.BannerDestroyable
import com.ads.banner.model.BannerPauseAble
import com.ads.banner.model.BannerResumeAble
import com.ads.banner.model.banner.AdmobBannerConfig
import com.ads.banner.model.banner.BannerAdConfig
import com.ads.banner.model.banner.MaxBannerConfig
import com.applovin.mediation.ads.MaxAdView
import com.applovin.sdk.AppLovinMediationProvider
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkInitializationConfiguration
import com.google.android.gms.ads.AdView
import com.wz.multipead.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private lateinit var bannerAdManager: IBannerAdManager

    private var bannerAd: BannerAd<*>? = null

    private fun getAdmobBannerConfig(): BannerAdConfig {
        return AdmobBannerConfig(adUnitId = "ca-app-pub-3940256099942544/6300978111")
    }

    private fun getMaxBannerConfig(parentView: ViewGroup): BannerAdConfig {
        return MaxBannerConfig(adUnitId = "2e627e3499187e00", parentView = parentView)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val initConfig = AppLovinSdkInitializationConfiguration.builder(
            "sJ15ca4POpBC2JHIEOf7xoye1fy55OxDDPQtcP2ced81fcJLVtTWhF3kT8vthO6xImtr946dol4twMiIkPmsrU",
            this
        ).setMediationProvider(AppLovinMediationProvider.MAX)
            .build()
        AppLovinSdk.getInstance(this).initialize(initConfig) { sdkConfig -> }

        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        bannerAdManager = BannerAdManager(this, AdNetworkType.APPLOVIN)
        val bannerAdConfig = getMaxBannerConfig(binding.flBannerAd)
        bannerAdManager.fetchBannerAd(config = bannerAdConfig, onSuccess = {
            bannerAd = it

            when (val ad = bannerAd?.ad) {
                is AdView -> {
                    binding.flBannerAd.addView(ad)
                }

                is MaxAdView -> {
                    binding.flBannerAd.addView(ad)
                }
            }
            binding.tvBannerAdIsLoading.visibility = View.GONE
        }, onFailure = {
            binding.flBannerAd.visibility = View.GONE
        })

    }

    override fun onResume() {
        super.onResume()
        (bannerAd as? BannerResumeAble)?.resume()
    }

    override fun onPause() {
        super.onPause()
        (bannerAd as? BannerPauseAble)?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        (bannerAd as? BannerDestroyable)?.destroy()

    }

}