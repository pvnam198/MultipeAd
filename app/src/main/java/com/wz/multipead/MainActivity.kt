package com.wz.multipead

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ads.admob.banner.AdmobBannerConfig
import com.ads.admob.banner.AdmobBannerRenderer
import com.ads.admob.interstitial.model.AdmobInterstitialAdConfig
import com.ads.admob.interstitial.presenter.AdmobInterstitialPresenterConfig
import com.ads.admob.nativead.model.AdmobNativeConfig
import com.ads.applovin.banner.ApplovinBannerConfig
import com.ads.applovin.banner.ApplovinBannerRenderer
import com.ads.applovin.interstitial.model.ApplovinInterstitialAdConfig
import com.ads.applovin.interstitial.presenter.ApplovinInterstitialPresenterConfig
import com.ads.banner.manager.BannerAdManager
import com.ads.banner.manager.BannerAdManagerImpl
import com.ads.banner.model.BannerAdConfig
import com.ads.banner.model.BannerDestroyable
import com.ads.banner.model.BannerPauseAble
import com.ads.banner.model.BannerResult
import com.ads.banner.model.BannerResumeAble
import com.ads.banner.render.BannerRendererManager
import com.ads.interstitial.manager.InterstitialManager
import com.ads.interstitial.manager.InterstitialManagerImpl
import com.ads.interstitial.model.InterstitialAdConfig
import com.ads.model.AdNetworkType
import com.ads.nativead.manager.NativeAdManager
import com.ads.nativead.manager.NativeAdManagerImpl
import com.ads.nativead.model.NativeConfig
import com.wz.multipead.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var bannerAdManager: BannerAdManager

    private var currentBanner: BannerResult? = null

    private lateinit var interstitialManager: InterstitialManager

    private fun getAdmobBannerConfig(): BannerAdConfig {
        return AdmobBannerConfig(
            adUnitId = "ca-app-pub-3940256099942544/6300978111",
            context = this
        )
    }

    private fun getMaxBannerConfig(parentView: ViewGroup): BannerAdConfig {
        return ApplovinBannerConfig(adUnitId = "2e627e3499187e00", parentView = parentView)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initBannerAd()
        initInterstitialAd()
        initNativeAd()
    }

    private fun initNativeAd() {
        val nativeAdConfigs: List<Pair<AdNetworkType, NativeConfig>> = listOf(
            AdNetworkType.ADMOB to AdmobNativeConfig(
                adUnitId = "ca-app-pub-3940256099942544/2247696110",
                context = this,
                shouldLoad = true
            )
        )

        val nativeAdManager: NativeAdManager = NativeAdManagerImpl(nativeAdConfigs)
        nativeAdManager.load()

        val nativeAdAdapter = NativeAdAdapter()
        binding.rvNativeAds.adapter = nativeAdAdapter
        getAndSetNativeAd(nativeAdManager, nativeAdAdapter)
    }

    private fun getAndSetNativeAd(
        nativeAdManager: NativeAdManager,
        nativeAdAdapter: NativeAdAdapter
    ) {
        Handler(Looper.getMainLooper()).postDelayed({
            nativeAdManager.getNativePresenter()?.let { nativeAdAdapter.add(it) }
            getAndSetNativeAd(nativeAdManager, nativeAdAdapter)
        }, 2000)
    }

    private fun initInterstitialAd() {
        val adNetworkConfigs: List<Pair<AdNetworkType, InterstitialAdConfig>> = listOf(
            AdNetworkType.ADMOB to AdmobInterstitialAdConfig(
                adUnitId = "ca-app-pub-3940256099942544/1033173712",
                context = this,
                shouldLoad = true
            ),
            AdNetworkType.APPLOVIN to ApplovinInterstitialAdConfig(
                adUnitId = "2814aced6a3ada0a",
                shouldLoad = true
            ),
        )

        interstitialManager = InterstitialManagerImpl(adNetworkConfigs)
        interstitialManager.load()

        binding.btnShowInterstitialAd.setOnClickListener {
            interstitialManager.show(
                listOf(
                    ApplovinInterstitialPresenterConfig(activity = this, shouldShow = true),
                    AdmobInterstitialPresenterConfig(activity = this, shouldShow = true)
                ),
                onComplete = {
                    Toast.makeText(this, "Interstitial ad completed", Toast.LENGTH_SHORT).show()
                    interstitialManager.load()
                }
            )
        }
    }

    private fun initBannerAd() {
        val adNetworkConfigs: List<Pair<AdNetworkType, BannerAdConfig>> = listOf(
            AdNetworkType.ADMOB to getAdmobBannerConfig(),
            AdNetworkType.APPLOVIN to getMaxBannerConfig(binding.flBannerAd)
        )
        bannerAdManager = BannerAdManagerImpl(adNetworkConfigs)
        bannerAdManager.fetchBannerAd(onSuccess = { result ->
            binding.tvBannerAdIsLoading.visibility = View.GONE
            binding.flBannerAd.visibility = View.VISIBLE
            val bannerRendererManager = BannerRendererManager()
            bannerRendererManager.registerRenderer(
                AdmobBannerRenderer(onRender = {
                    binding.flBannerAd.addView(it)
                })
            )
            bannerRendererManager.registerRenderer(
                ApplovinBannerRenderer(onRender = { binding.flBannerAd.addView(it) })
            )
            bannerRendererManager.render(result)
            currentBanner = result
        }, onFailure = {
            binding.flBannerAd.visibility = View.GONE
        })
    }

    override fun onResume() {
        super.onResume()
        (currentBanner as? BannerResumeAble)?.resume()
    }

    override fun onPause() {
        super.onPause()
        (currentBanner as? BannerPauseAble)?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        (currentBanner as? BannerDestroyable)?.destroy()
    }

}