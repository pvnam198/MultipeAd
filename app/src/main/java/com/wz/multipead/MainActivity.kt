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
import com.ads.admob.interstitial.presenter.AdmobOpenAdPresenterConfig
import com.ads.admob.nativead.model.AdmobNativeConfig
import com.ads.admob.open.AdmobOpenAdConfig
import com.ads.admob.rewarded.model.AdmobRewardedAdConfig
import com.ads.admob.rewarded.presenter.AdmobRewardedPresenterConfig
import com.ads.applovin.banner.ApplovinBannerConfig
import com.ads.applovin.banner.ApplovinBannerRenderer
import com.ads.applovin.interstitial.model.ApplovinInterstitialAdConfig
import com.ads.applovin.interstitial.presenter.ApplovinInterstitialPresenterConfig
import com.ads.applovin.nativead.model.ApplovinNativeConfig
import com.ads.applovin.open.ApplovinOpenAdConfig
import com.ads.applovin.rewarded.model.ApplovinRewardedAdConfig
import com.ads.applovin.rewarded.presenter.ApplovinRewardedPresenterConfig
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
import com.ads.nativead.model.getter.NativeAdByIdGetter
import com.ads.open.loader.OpenAdConfig
import com.ads.open.manager.OpenAdManager
import com.ads.open.manager.OpenAdManagerImpl
import com.ads.open.presenter.OpenAdPresenterListener
import com.ads.rewarded.manager.RewardedManager
import com.ads.rewarded.manager.RewardedManagerImpl
import com.ads.rewarded.model.RewardedAdConfig
import com.ads.rewarded.presenter.RewardedAdFailed
import com.ads.rewarded.presenter.UserEarnedReward
import com.wz.multipead.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var bannerAdManager: BannerAdManager

    private var currentBanner: BannerResult? = null

    private lateinit var interstitialManager: InterstitialManager

    private lateinit var rewardedManager: RewardedManager

    private lateinit var openAdManager: OpenAdManager

    private fun getAdmobBannerConfig(): BannerAdConfig {
        return AdmobBannerConfig(
            adUnitId = "ca-app-pub-3940256099942544/6300978111", context = this
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
        initRewardAd()
        initOpenAd()
    }

    private fun initOpenAd() {
        val adConfigs: List<Pair<AdNetworkType, OpenAdConfig>> = listOf(
            AdNetworkType.ADMOB to AdmobOpenAdConfig(
                adUnitId = "ca-app-pub-3940256099942544/9257395921",
                context = this
            ),
            AdNetworkType.APPLOVIN to ApplovinOpenAdConfig(
                adUnitId = "dc3f9774772c3407",
            ),
        )
        openAdManager = OpenAdManagerImpl(adConfigs)
        openAdManager.load()

        binding.btnShowOpenAd.setOnClickListener {
            openAdManager.show(
                listOf(
                    AdmobOpenAdPresenterConfig(
                        this,
                        object : OpenAdPresenterListener {
                            override fun onShowAdComplete(msg: String?) {
                                Toast.makeText(this@MainActivity, "$msg", Toast.LENGTH_SHORT).show()
                                openAdManager.load()
                            }
                        })
                ), onComplete = {
                    Toast.makeText(this, "Open ad completed", Toast.LENGTH_SHORT).show()
                    openAdManager.load()
                })
        }
    }

    private fun initRewardAd() {
        val rewardedAdConfigs: List<Pair<AdNetworkType, RewardedAdConfig>> = listOf(
            AdNetworkType.APPLOVIN to ApplovinRewardedAdConfig(
                adUnitId = "9e39151540f3ffda", true
            ),

            AdNetworkType.ADMOB to AdmobRewardedAdConfig(
                adUnitId = "ca-app-pub-3940256099942544/5224354917", this, true
            )
        )

        rewardedManager = RewardedManagerImpl(rewardedAdConfigs)
        rewardedManager.load()

        binding.btnShowRewardedAd.setOnClickListener {
            rewardedManager.show(
                listOf(
                    ApplovinRewardedPresenterConfig(activity = this, shouldShow = true),
                    AdmobRewardedPresenterConfig(activity = this, shouldShow = true)
                ), onComplete = {
                    when (it) {
                        is UserEarnedReward -> {
                            Toast.makeText(this, "Reward Earned", Toast.LENGTH_SHORT).show()
                        }

                        is RewardedAdFailed -> {
                            Toast.makeText(this, "Reward Failed", Toast.LENGTH_SHORT).show()
                        }

                        else -> {
                            Toast.makeText(this, "Reward Failed", Toast.LENGTH_SHORT).show()
                        }

                    }
                    rewardedManager.load()
                })

        }
    }


    private fun initNativeAd() {
        val adConfigs: List<Pair<AdNetworkType, NativeConfig>> = listOf(
            AdNetworkType.ADMOB to AdmobNativeConfig(
                adUnitId = "ca-app-pub-3940256099942544/2247696110",
                context = this,
                shouldLoad = true
            ),
            AdNetworkType.APPLOVIN to ApplovinNativeConfig(
                adUnitId = "202fad7ccc3a236b",
                this
            ),
        )

        val nativeAdManager: NativeAdManager = NativeAdManagerImpl(adConfigs)
        nativeAdManager.load()

        val nativeAdAdapter = NativeAdAdapter()
        binding.rvNativeAds.adapter = nativeAdAdapter
        getAndSetNativeAd(nativeAdManager, nativeAdAdapter)
    }

    private fun getAndSetNativeAd(
        nativeAdManager: NativeAdManager, nativeAdAdapter: NativeAdAdapter
    ) {
        Handler(Looper.getMainLooper()).postDelayed({
            val presenter =
                nativeAdManager.getNativePresenter(NativeAdByIdGetter("ca-app-pub-3940256099942544/2247696110"))
                    ?: nativeAdManager.getNativePresenter()
            presenter?.let { nativeAdAdapter.add(it) }
            getAndSetNativeAd(nativeAdManager, nativeAdAdapter)
        }, 15000)
    }

    private fun initInterstitialAd() {
        val adNetworkConfigs: List<Pair<AdNetworkType, InterstitialAdConfig>> = listOf(
            AdNetworkType.ADMOB to AdmobInterstitialAdConfig(
                adUnitId = "ca-app-pub-3940256099942544/1033173712",
                context = this,
                shouldLoad = true
            ),
            AdNetworkType.APPLOVIN to ApplovinInterstitialAdConfig(
                adUnitId = "94e4dd1f78bdab66",
                shouldLoad = true
            ),
        )

        interstitialManager = InterstitialManagerImpl(adNetworkConfigs)
        interstitialManager.load()

        binding.btnLaunchMediationDebugger.setOnClickListener {
            (it.context.applicationContext as App).applovinAdInitializer?.showMediation()
        }

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
            AdNetworkType.APPLOVIN to getMaxBannerConfig(binding.flBannerAd),
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