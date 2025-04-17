package com.ads.admob.banner

import com.ads.banner.model.banner.BannerAd
import com.ads.banner.model.banner.BannerDestroyable
import com.ads.banner.model.banner.BannerPauseAble
import com.ads.banner.model.banner.BannerResumeAble
import com.google.android.gms.ads.AdView

class AdmobBanner(
    private val adView: AdView
) : BannerAd<AdView>(adView), BannerPauseAble, BannerDestroyable, BannerResumeAble {

    override fun resume() {
        adView.resume()
    }

    override fun pause() {
        adView.pause()
    }

    override fun destroy() {
        adView.destroy()
    }
}