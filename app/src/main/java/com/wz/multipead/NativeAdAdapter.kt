package com.wz.multipead

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ads.admob.nativead.presenter.AdmobNativeAdPresenter
import com.ads.admob.nativead.presenter.AdmobNativeAdPresenterConfig
import com.ads.admob.nativead.presenter.CustomAdmobNativeView
import com.ads.applovin.nativead.presenter.CustomMaxNativeView
import com.ads.applovin.nativead.presenter.MaxNativeAdPresenter
import com.ads.applovin.nativead.presenter.MaxNativeAdPresenterConfig
import com.ads.nativead.presenter.NativeAdPresenter

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

    inner class AdmobHolder(private val view: CustomAdmobNativeView) :
        RecyclerView.ViewHolder(view) {

        fun bind(nativeAdPresenter: NativeAdPresenter) {
            val context = itemView.context
            val shouldShow = true

            // Tạo config với adContainer
            val config = AdmobNativeAdPresenterConfig(
                context = context,
                shouldShow = shouldShow,
                adContainer = view
            )

            // Gọi presenter để hiển thị quảng cáo
            nativeAdPresenter.show(config = config) {
                Log.e("AdmobHolder", "Failed to show ad")
            }
        }
    }

    inner class ApplovinHolder(private val view: CustomMaxNativeView) :
        RecyclerView.ViewHolder(view) {

        fun bind(nativeAdPresenter: NativeAdPresenter) {

            val context = itemView.context
            val shouldShow = true

            val config = MaxNativeAdPresenterConfig(
                context = context,
                shouldShow = shouldShow,
                adContainer = view
            )

            nativeAdPresenter.show(config = config) {
                Log.e("ApplovinHolder", "Failed to show ad")
            }

        }

    }

}