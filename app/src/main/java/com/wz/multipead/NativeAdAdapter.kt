package com.wz.multipead

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ads.admob.nativead.presenter.AdmobNativeAdPresenter
import com.ads.admob.nativead.presenter.AdmobNativeAdPresenterConfig
import com.ads.admob.nativead.presenter.CustomAdmobNativeView
import com.ads.applovin.nativead.presenter.CustomMaxNativeView
import com.ads.applovin.nativead.presenter.MaxNativeAdPresenter
import com.ads.applovin.nativead.presenter.MaxNativeAdPresenterConfig
import com.ads.nativead.listener.NativeAdCloseListener
import com.ads.nativead.model.DisplayableNativeAd
import com.ads.nativead.presenter.CloseableNativeAd
import com.ads.nativead.presenter.NativeAdPresenter
import com.ads.nativead.presenter.NativeAdPresenterConfig

class NativeAdAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val nativeAdPresenters = mutableListOf<NativeAdPresenter>()

    companion object {

        private const val TYPE_ADMOB = 0

        private const val TYPE_APPLOVIN = 1

    }

    fun add(nativeAdPresenter: NativeAdPresenter) {
        nativeAdPresenters.add(nativeAdPresenter)
        notifyItemChanged(nativeAdPresenters.indexOf(nativeAdPresenter))
    }

    override fun getItemViewType(position: Int): Int {
        return when (nativeAdPresenters[position]) {
            is AdmobNativeAdPresenter -> TYPE_ADMOB
            is MaxNativeAdPresenter -> TYPE_APPLOVIN
            else -> throw Exception("Unknown view type")
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ADMOB -> {
                val view = CustomAdmobNativeView(parent.context)
                view.setAdLayout(R.layout.admob_native_view)
                return AdmobHolder(view)
            }

            TYPE_APPLOVIN -> {
                val view = CustomMaxNativeView(parent.context)
                view.setAdLayout(R.layout.max_native_view)
                return ApplovinHolder(view)
            }

            else -> throw Exception("Unknown view type")

        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int
    ) {
        when (holder) {
            is AdmobHolder -> holder.bind(nativeAdPresenters[position])
            is ApplovinHolder -> holder.bind(nativeAdPresenters[position])
        }
    }

    override fun getItemCount(): Int {
        return nativeAdPresenters.size
    }

    abstract inner class BasePresenterHolder(view: View) : RecyclerView.ViewHolder(view) {

        abstract fun getNativeAdPresenterConfig(): NativeAdPresenterConfig

        open fun setCloseListener(nativeAdPresenter: NativeAdPresenter) {
            if (nativeAdPresenter is CloseableNativeAd) {
                nativeAdPresenter.adCloseListener = object : NativeAdCloseListener {
                    override fun onAdClosed() {
                        handleAdClosed()
                    }
                }
            }
        }

        open fun handleAdClosed() {
            Log.d("AdmobHolder", "onAdClosed: ")
        }

        open fun handleAdShowFailed(msg: String?) {
            Log.d("AdmobHolder", "handleAdShowFailed: $msg")
        }

        open fun bind(nativeAdPresenter: NativeAdPresenter) {

            setCloseListener(nativeAdPresenter)

            DisplayableNativeAd.show(
                presenter = nativeAdPresenter,
                config = getNativeAdPresenterConfig(),
                onFailure = { msg ->
                    handleAdShowFailed(msg)
                }
            )
        }

    }

    inner class AdmobHolder(private val view: CustomAdmobNativeView) : BasePresenterHolder(view) {
        override fun getNativeAdPresenterConfig(): NativeAdPresenterConfig {
            val context = itemView.context
            val shouldShow = true
            return AdmobNativeAdPresenterConfig(
                context = context,
                shouldShow = shouldShow,
                adContainer = view
            )
        }
    }

    inner class ApplovinHolder(private val view: CustomMaxNativeView) : BasePresenterHolder(view) {
        override fun getNativeAdPresenterConfig(): NativeAdPresenterConfig {
            val context = itemView.context
            val shouldShow = true

            return MaxNativeAdPresenterConfig(
                context = context,
                shouldShow = shouldShow,
                adContainer = view
            )
        }
    }

}