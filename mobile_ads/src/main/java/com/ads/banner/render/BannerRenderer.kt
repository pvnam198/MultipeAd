package com.ads.banner.render

import com.ads.banner.model.banner.BannerResult

interface BannerRenderer {

    fun canRender(bannerResult: BannerResult): Boolean

    fun render(bannerResult: BannerResult)
}