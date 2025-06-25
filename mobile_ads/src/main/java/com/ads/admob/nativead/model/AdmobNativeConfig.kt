package com.ads.admob.nativead.model

import android.content.Context
import com.ads.nativead.model.NativeConfig

class AdmobNativeConfig(
    val adUnitId: List<String>,
    val context: Context,
    val shouldLoad: Boolean = true,
    val maxLoadNative: Int = 2
) : NativeConfig