package com.ads.admob.nativead.presenter

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.google.android.gms.ads.nativead.MediaView

class AdmobMediaView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    init {
        val mediaView = MediaView(context)
        addView(mediaView)
    }

    val mediaView: MediaView get() = getChildAt(0) as MediaView

}