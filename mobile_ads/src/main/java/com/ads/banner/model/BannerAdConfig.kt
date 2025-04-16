package com.ads.banner.model

data class BannerAdConfig(
    val adUnitId: String,
    val adSize: BannerSize = BannerSize.Banner,
    val isCollapsible: Boolean = false
) {
    class Builder {
        private var adSize: BannerSize = BannerSize.Banner
        private var isCollapsible: Boolean = false

        fun setAdSize(size: BannerSize) = apply { this.adSize = size }
        fun setCollapsible(collapsible: Boolean) = apply { this.isCollapsible = collapsible }

        fun build(adUnitId: String): BannerAdConfig {
            return BannerAdConfig(
                adUnitId = adUnitId,
                adSize = adSize,
                isCollapsible = isCollapsible
            )
        }
    }
}
