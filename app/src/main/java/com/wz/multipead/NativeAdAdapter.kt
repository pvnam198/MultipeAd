package com.wz.multipead

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ads.admob.nativead.presenter.AdmobNativeAdPresenter
import com.ads.admob.nativead.presenter.AdmobNativeAdPresenterConfig
import com.ads.admob.nativead.presenter.CustomAdmobNativeView
import com.ads.applovin.nativead.presenter.ApplovinNativeAdPresenter
import com.ads.nativead.presenter.NativeAdPresenter
import com.wz.multipead.databinding.AdmobNativeViewBinding
import com.wz.multipead.databinding.ItemApplovinNativeBinding

class NativeAdAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val nativeAdPresenters = mutableListOf<NativeAdPresenter>()

    companion object {

        private const val TYPE_ADMOB = 0

        private const val TYPE_APPLOVIN = 1

    }

    fun add(nativeAdPresenter: NativeAdPresenter) {
        Log.d("log_test_123123131", "add: $nativeAdPresenter")
        nativeAdPresenters.add(nativeAdPresenter)
        notifyItemChanged(nativeAdPresenters.indexOf(nativeAdPresenter))
    }

    override fun getItemViewType(position: Int): Int {
        return when (nativeAdPresenters[position]) {
            is AdmobNativeAdPresenter -> TYPE_ADMOB
            is ApplovinNativeAdPresenter -> TYPE_APPLOVIN
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
                val binding = ItemApplovinNativeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ApplovinHolder(binding)
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

    inner class ApplovinHolder(private val binding: ItemApplovinNativeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(nativeAdPresenter: NativeAdPresenter) {
//            val context = itemView.context
//            val shouldShow = true
//            val adContainer = itemView as ViewGroup
//            val admobNativeViewBinding =
//                AdmobNativeViewBinding.inflate(LayoutInflater.from(context), binding.root, false)
//            val adHeadline = admobNativeViewBinding.adHeadline
//            val adMediaView = admobNativeViewBinding.adMedia
//            val bodyTextView = admobNativeViewBinding.adBody
//            val callToActionTextView = admobNativeViewBinding.adCallToAction
//            val iconImageView = admobNativeViewBinding.adAppIcon
//            val priceTextView = admobNativeViewBinding.adPrice
//            val starsRatingBar = admobNativeViewBinding.adStars
//            val storeTextView = admobNativeViewBinding.adStore
//            val advertiserTextView = admobNativeViewBinding.adAdvertiser
//            val admobNativeAdPresenterConfig = AdmobNativeAdPresenterConfig(
//                context = context,
//                shouldShow = shouldShow,
//                adContainer = adContainer,
//                adMediaView = adMediaView,
//                adHeadline = adHeadline,
//                adBody = bodyTextView,
//                adCallToAction = callToActionTextView,
//                adIcon = iconImageView,
//                adPrice = priceTextView,
//                adStars = starsRatingBar,
//                adStore = storeTextView,
//                adAdvertiser = advertiserTextView
//            )
//            nativeAdPresenter.show(config = admobNativeAdPresenterConfig) { }
        }

    }

}