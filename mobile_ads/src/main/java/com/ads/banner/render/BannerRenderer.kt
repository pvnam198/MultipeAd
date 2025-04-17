package com.ads.banner.render

import com.ads.banner.model.BannerResult

interface BannerRenderer {

    fun canRender(bannerResult: BannerResult): Boolean

    fun render(bannerResult: BannerResult)
}