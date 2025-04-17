package com.wz.multipead

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ads.AdNetworkType
import com.ads.admob.banner.AdmobBannerRenderer
import com.ads.applovin.banner.ApplovinBannerRenderer
import com.ads.banner.manager.BannerAdManagerImpl
import com.ads.banner.manager.BannerAdManager
import com.ads.banner.model.banner.BannerDestroyable
import com.ads.banner.model.banner.BannerPauseAble
import com.ads.banner.model.banner.BannerResumeAble
import com.ads.admob.banner.AdmobBannerConfig
import com.ads.banner.model.banner.BannerAdConfig
import com.ads.banner.model.banner.BannerResult
import com.ads.applovin.banner.ApplovinBannerConfig
import com.ads.banner.render.BannerRendererManager
import com.wz.multipead.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var bannerAdManager: BannerAdManager

    private var currentBanner: BannerResult? = null

    private fun getAdmobBannerConfig(): BannerAdConfig {
        return AdmobBannerConfig(adUnitId = "ca-app-pub-3940256099942544/6300978111")
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
        bannerAdManager = BannerAdManagerImpl(this, listOf(AdNetworkType.APPLOVIN))
        val bannerAdConfig = getMaxBannerConfig(binding.flBannerAd)
        bannerAdManager.fetchBannerAd(config = bannerAdConfig, onSuccess = { result ->
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