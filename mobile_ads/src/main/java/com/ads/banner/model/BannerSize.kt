package com.ads.banner.model

sealed class BannerSize(val width: Int, val height: Int) {
    data object Banner : BannerSize(320, 50)
    data object LargeBanner : BannerSize(320, 100)
    data object MediumRectangle : BannerSize(300, 250)
    data class Custom(val w: Int, val h: Int) : BannerSize(w, h)
}