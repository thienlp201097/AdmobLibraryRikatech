package com.vapp.admobexample.utilsdemp

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.nativead.NativeAd
import com.vapp.admobexample.R
import com.vapp.admoblibrary.AdsInterCallBack
import com.vapp.admoblibrary.ads.AdCallBackInterLoad
import com.vapp.admoblibrary.ads.AdmodUtils
import com.vapp.admoblibrary.ads.AppOpenManager
import com.vapp.admoblibrary.ads.NativeAdCallback
import com.vapp.admoblibrary.ads.admobnative.enumclass.CollapsibleBanner
import com.vapp.admoblibrary.ads.admobnative.enumclass.GoogleENative
import com.vapp.admoblibrary.ads.model.BannerAdCallback
import com.vapp.admoblibrary.ads.model.InterHolder
import com.vapp.admoblibrary.ads.model.NativeHolder
import com.vapp.admoblibrary.utils.Utils

object AdsManager {
    var interAds1: InterstitialAd? = null
    val mutable_inter1: MutableLiveData<InterstitialAd> = MutableLiveData()
    var check_inter1 = false

    var nativeHolder = NativeHolder(
        "ca-app-pub-3940256099942544/2247696110",
        "ca-app-pub-3940256099942544/2247696110"
    )
    var interholder = InterHolder(
        "ca-app-pub-3940256099942544/1033173712",
        "ca-app-pub-3940256099942544/1033173712"
    )

    fun loadInter(context: Context, interHolder: InterHolder) {
        AdmodUtils.loadAndGetAdInterstitial(context, interHolder, object :
            AdCallBackInterLoad {
            override fun onAdClosed() {

            }

            override fun onEventClickAdClosed() {

            }

            override fun onAdShowed() {

            }

            override fun onAdLoaded(interstitialAd: InterstitialAd?, isLoading: Boolean) {

            }

            override fun onAdFail(isLoading: Boolean) {
            }
        })
    }

    fun showAdInter(context: Context, interHolder: InterHolder, callback: AdListener, ) {
        AppOpenManager.getInstance().isAppResumeEnabled = true
        AdmodUtils.showAdInterstitialWithCallbackNotLoadNew(
            context as Activity,
            interHolder,
            10000,
            object :
                AdsInterCallBack {
                override fun onStartAction() {
                    callback.onAdClosedOrFailed()
                }

                override fun onEventClickAdClosed() {
//                    loadInter(context, interHolder)
                }

                override fun onAdShowed() {
                    Handler().postDelayed({
                        try {
                            AdmodUtils.dismissAdDialog()
                        } catch (_: Exception) {

                        }
                    }, 800)
                }

                override fun onAdLoaded() {

                }

                override fun onAdFail(error: String?) {
                    Log.d("===Failed", error.toString())
                    val log = error?.split(":")?.get(0)?.replace(" ", "_")
//                    loadInter(context, interHolder)
                    callback.onAdClosedOrFailed()
                }

                override fun onPaid(adValue: AdValue?) {

                }
            },
            true
        )
    }

    fun loadAndShowIntersial(activity: Activity, adListener: AdListener) {
        AdmodUtils.loadAndShowAdInterstitialWithCallbackMultiAds(activity as AppCompatActivity, "", "",
            object : AdsInterCallBack {
                override fun onStartAction() {
                    adListener.onAdClosedOrFailed()
                }

                override fun onEventClickAdClosed() {

                }

                override fun onAdShowed() {

                }

                override fun onAdLoaded() {

                }

                override fun onAdFail(error: String) {
                    val log = error.split(":")[0].replace(" ", "_")
                    Log.d("===ADS", log)
                    adListener.onAdClosedOrFailed()
                }

                override fun onPaid(adValue: AdValue?) {
                }
            },
            true
        )
    }

    fun loadNative(activity: Context, nativeHolder: NativeHolder) {
        AdmodUtils.loadAndGetNativeAds(activity, nativeHolder, object : NativeAdCallback {
                override fun onLoadedAndGetNativeAd(ad: NativeAd?) {
                }

                override fun onNativeAdLoaded() {

                }

                override fun onAdFail(error: String) {
                    Log.d("===AdsLoadsNative", error)
                }

                override fun onAdPaid(adValue: AdValue?) {

                }
            })
    }

    fun showAdNativeSmall(activity: Activity, nativeAdContainer: ViewGroup, nativeHolder: NativeHolder) {
        if (!AdmodUtils.isNetworkConnected(activity)) {
            nativeAdContainer.visibility = View.GONE
            return
        }
        AdmodUtils.showNativeAdsWithLayout(activity, nativeHolder, nativeAdContainer, R.layout.ad_template_small, GoogleENative.UNIFIED_SMALL, object : AdmodUtils.AdsNativeCallBackAdmod {
                override fun NativeLoaded() {
                    Log.d("===NativeAds", "Native showed")
                    nativeAdContainer.visibility = View.VISIBLE
                }

                override fun NativeFailed() {
                    Log.d("===NativeAds", "Native false")
                    nativeAdContainer.visibility = View.GONE
                }
            }
        )
    }

    fun showAdNativeMedium(activity: Activity, nativeAdContainer: ViewGroup, nativeHolder: NativeHolder) {
        if (!AdmodUtils.isNetworkConnected(activity)) {
            nativeAdContainer.visibility = View.GONE
            return
        }
        AdmodUtils.showNativeAdsWithLayout(activity, nativeHolder, nativeAdContainer, R.layout.ad_template_medium, GoogleENative.UNIFIED_MEDIUM, object : AdmodUtils.AdsNativeCallBackAdmod {
            override fun NativeLoaded() {
                Log.d("===NativeAds", "Native showed")
                nativeAdContainer.visibility = View.VISIBLE
            }

            override fun NativeFailed() {
                Log.d("===NativeAds", "Native false")
                nativeAdContainer.visibility = View.GONE
            }
        }
        )
    }

    @JvmStatic
    fun showAdBannerCollapsible(activity: Activity, adsEnum: String, view: ViewGroup, line: View) {
        if (AdmodUtils.isNetworkConnected(activity)) {
            AdmodUtils.loadAdBannerCollapsible(
                activity,
                adsEnum,
                CollapsibleBanner.BOTTOM,
                view,
                object : BannerAdCallback {
                    override fun onBannerAdLoaded(adSize: AdSize) {
                        view.visibility = View.VISIBLE
                        line.visibility = View.VISIBLE
                        val params: ViewGroup.LayoutParams = view.layoutParams
                        params.height = adSize.getHeightInPixels(activity)
                        view.layoutParams = params
                    }

                    override fun onAdFail() {
                        view.visibility = View.GONE
                        line.visibility = View.GONE
                    }

                    override fun onAdPaid(adValue: AdValue?) {

                    }
                })
        } else {
            view.visibility = View.GONE
            line.visibility = View.GONE
        }
    }

    @JvmStatic
    fun showAdBanner(activity: Activity, adsEnum: String, view: ViewGroup, line: View) {
        if (AdmodUtils.isNetworkConnected(activity)) {
            AdmodUtils.loadAdBanner(activity, adsEnum, view, object :
                AdmodUtils.BannerCallBack {
                override fun onLoad() {
                    view.visibility = View.VISIBLE
                    line.visibility = View.VISIBLE
                }

                override fun onFailed() {
                    view.visibility = View.GONE
                    line.visibility = View.GONE
                }

                override fun onPaid(adValue: AdValue?, mAdView: AdView?) {
                }
            })
        } else {
            view.visibility = View.GONE
            line.visibility = View.GONE
        }
    }

    interface AdListener {
        fun onAdClosedOrFailed()
    }
}
