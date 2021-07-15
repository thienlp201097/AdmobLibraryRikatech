package com.vapp.admoblibrary.ads.admobnative

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.ads.google.admobads.admobnative.GoogleENative
import com.google.android.gms.ads.VideoController
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.vapp.admoblibrary.R

class NativeFunc {

    companion object{
        fun populateNativeAdView(
            nativeAd: NativeAd,
            adView: NativeAdView,
            size: GoogleENative
        ) {
            if(nativeAd==null || adView == null || size == null){
                return
            }

            if (nativeAd.body == null || nativeAd.callToAction == null){
                return
            }

            adView.findViewById<MediaView>(R.id.ad_media)?.let {
                adView.mediaView = it
            }
            adView.findViewById<TextView>(R.id.ad_headline)?.let {
                adView.headlineView = it
            }
            adView.findViewById<TextView>(R.id.ad_body)?.let {
                adView.bodyView = it
            }
            adView.findViewById<Button>(R.id.ad_call_to_action)?.let {
                adView.callToActionView = it
            }
            adView.findViewById<ImageView>(R.id.ad_app_icon)?.let {
                adView.iconView = it
            }
            adView.findViewById<RatingBar>(R.id.ad_stars)?.let {
                adView.starRatingView = it
            }

            if (size == GoogleENative.UNIFIED_MEDIUM) {
                adView.mediaView.setMediaContent(nativeAd.mediaContent)
            }

            (adView.headlineView as TextView).text = nativeAd.headline
            if (nativeAd.body == null) {
                adView.bodyView.visibility = View.INVISIBLE
            } else {
                adView.bodyView.visibility = View.VISIBLE
                (adView.bodyView as TextView).text = nativeAd.body
            }
            if (nativeAd.callToAction == null) {
                adView.callToActionView.visibility = View.INVISIBLE
            } else {
                adView.callToActionView.visibility = View.VISIBLE
                (adView.callToActionView as Button).text = nativeAd.callToAction
            }



            if (nativeAd.icon == null) {
                adView.iconView.visibility = View.GONE
            } else {

                (adView.iconView as ImageView).setImageDrawable(
                    nativeAd.icon.drawable
                )
                adView.iconView.visibility = View.VISIBLE
            }
            if (nativeAd.starRating == null) {
                adView.starRatingView.visibility = View.INVISIBLE
            } else {
                (adView.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
                adView.starRatingView.visibility = View.VISIBLE
            }
            adView.setNativeAd(nativeAd)
            val vc = nativeAd.mediaContent.videoController
            if (vc.hasVideoContent()) {
                vc.videoLifecycleCallbacks = object : VideoController.VideoLifecycleCallbacks() {
                    override fun onVideoEnd() {
                        super.onVideoEnd()
                    }
                }
            }
        }

    }
}