package com.ads.admob.nativead.model

import android.content.Context
import com.ads.nativead.model.NativeConfig

class AdmobNativeConfig(
    override val adUnitId: String,
    val context: Context,
    val shouldLoad: Boolean = true
) : NativeConfig