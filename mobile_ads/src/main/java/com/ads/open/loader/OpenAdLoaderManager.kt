package com.ads.open.loader

import com.ads.model.AdFailure
import com.ads.open.presenter.OpenAdPresenter

interface OpenAdLoaderManager {
    fun load(
        onComplete: (OpenAdPresenter) -> Unit,
        onFailure: (List<AdFailure>) -> Unit
    )
}