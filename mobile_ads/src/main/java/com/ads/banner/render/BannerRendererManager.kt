package com.ads.banner.render

import com.ads.banner.model.banner.BannerResult

class BannerRendererManager {

    private val renderers = mutableSetOf<BannerRenderer>()

    fun registerRenderer(renderer: BannerRenderer) {
        renderers.add(renderer)
    }

    fun unregisterRenderer(renderer: BannerRenderer) {
        renderers.remove(renderer)
    }

    fun render(bannerResult: BannerResult) {
        renderers.forEach { it.render(bannerResult) }
    }

}