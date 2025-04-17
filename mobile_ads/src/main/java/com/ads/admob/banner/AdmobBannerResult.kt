package com.ads.admob.banner

import android.view.View
import com.ads.banner.model.BannerDestroyable
import com.ads.banner.model.BannerPauseAble
import com.ads.banner.model.BannerResumeAble
import com.ads.banner.model.BannerResult

class AdmobBannerResult(
    val adView: View,
    private val onResume: () -> Unit,
    private val onPause: () -> Unit,
    private val onDestroy: () -> Unit
) : BannerResult, BannerResumeAble, BannerPauseAble, BannerDestroyable {
    override fun resume() = onResume()

    override fun pause() = onPause()

    override fun destroy() = onDestroy()
}