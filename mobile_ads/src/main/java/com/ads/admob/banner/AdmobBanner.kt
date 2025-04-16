package com.ads.admob.banner

import com.ads.banner.model.BannerAd
import com.ads.banner.model.BannerDestroyable
import com.ads.banner.model.BannerPauseAble
import com.ads.banner.model.BannerResumeAble
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