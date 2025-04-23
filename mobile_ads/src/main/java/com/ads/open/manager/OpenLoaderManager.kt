package com.ads.open.manager

import com.ads.model.AdFailure
import com.ads.open.presenter.OpenAdPresenter

interface OpenLoaderManager {
    fun load(
        onComplete: (OpenAdPresenter) -> Unit,
        onFailure: (List<AdFailure>) -> Unit
    )
}