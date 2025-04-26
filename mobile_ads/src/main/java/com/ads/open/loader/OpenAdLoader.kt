package com.ads.open.loader

import com.ads.open.presenter.OpenAdPresenter

interface OpenAdLoader {

    fun load(
        adConfig: OpenAdConfig,
        onSuccess: (openAdPresenter: OpenAdPresenter) -> Unit,
        onFailure: (msg: String?) -> Unit
    )

}