package com.ads.banner.model.banner.render

import com.ads.banner.model.banner.BannerResult

interface BannerRenderer {
    fun render(bannerResult: BannerResult)
}