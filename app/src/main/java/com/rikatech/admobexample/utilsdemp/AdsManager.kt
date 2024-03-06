package com.rikatech.admobexample.utilsdemp

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MediaAspectRatio
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.nativead.NativeAd
import com.rikatech.admobexample.R
import com.rikatech.admoblibrary.ads.AdmobRikatech
import com.rikatech.admoblibrary.ads.AppOpenManager
import com.rikatech.admoblibrary.ads.admobnative.enumclass.CollapsibleBanner
import com.rikatech.admoblibrary.ads.admobnative.enumclass.GoogleENative
import com.rikatech.admoblibrary.ads.model.InterHolder
import com.rikatech.admoblibrary.ads.model.NativeHolder
import com.rikatech.admoblibrary.ads.model.RewardedInterstitialHolder
import com.rikatech.admoblibrary.ads.remote.BannerPlugin
import com.rikatech.admoblibrary.callback.AdCallBackInterLoad
import com.rikatech.admoblibrary.callback.AdLoadCallback
import com.rikatech.admoblibrary.callback.AdsInterCallBack
import com.rikatech.admoblibrary.callback.BannerRemoteConfig
import com.rikatech.admoblibrary.callback.NativeAdCallback
import com.rikatech.admoblibrary.callback.NativeFullScreenCallBack
import com.rikatech.admoblibrary.callback.RewardAdCallback

object AdsManager {
    var nativeHolder = NativeHolder("")
    var inter_holder = InterHolder("")
    var interRewardHolder = RewardedInterstitialHolder("")
    fun loadAndShowBannerRemote(
        activity: Activity,
        id: String,
        bannerConfig: BannerPlugin.BannerConfig?,
        view: ViewGroup,
        line: View
    ) {
        BannerPlugin(activity, view, id, bannerConfig, object : BannerRemoteConfig {
            override fun onBannerAdLoaded(adSize: AdSize?) {
                view.visibility = View.VISIBLE
                line.visibility = View.VISIBLE
            }

            override fun onAdFail() {
                view.visibility = View.GONE
                line.visibility = View.GONE
            }

            override fun onAdPaid(adValue: AdValue, mAdView: AdView) {
            }
        }
        )
    }

    fun loadAndShowNative(
        activity: Activity,
        nativeAdContainer: ViewGroup,
        nativeHolder: NativeHolder
    ) {
        if (!AdmobRikatech.isNetworkConnected(activity)) {
            nativeAdContainer.visibility = View.GONE
            return
        }
        AdmobRikatech.loadAndShowNativeAdsWithLayoutAds(
            activity,
            nativeHolder,
            nativeAdContainer,
            R.layout.ad_template_medium,
            GoogleENative.UNIFIED_MEDIUM,
            object : AdmobRikatech.NativeAdCallbackNew {
                override fun onLoadedAndGetNativeAd(ad: NativeAd?) {
                }

                override fun onNativeAdLoaded() {
                    Log.d("===nativeload", "true")
                    nativeAdContainer.visibility = View.VISIBLE
                }

                override fun onAdFail(error: String) {
                    nativeAdContainer.visibility = View.GONE
                }

                override fun onAdPaid(adValue: AdValue?, adUnitAds: String?) {
                    Log.d("===AdValue", "Native: ${adValue?.currencyCode}|${adValue?.valueMicros}")
                }

                override fun onClickAds() {

                }
            })
    }

    fun loadInter(context: Context, interHolder: InterHolder) {
        AdmobRikatech.loadAndGetAdInterstitial(context, interHolder, object :
            AdCallBackInterLoad {
            override fun onAdClosed() {

            }

            override fun onEventClickAdClosed() {

            }

            override fun onAdShowed() {

            }

            override fun onAdLoaded(interstitialAd: InterstitialAd?, isLoading: Boolean) {
                Log.d("===ResponseInfo",interstitialAd?.responseInfo.toString())
            }

            override fun onAdFail(message: String?) {
            }
        })
    }

    fun showAdInter(context: Context, interHolder: InterHolder, callback: AdListener) {
        AppOpenManager.getInstance().isAppResumeEnabled = true
        AdmobRikatech.showAdInterstitialWithCallbackNotLoadNew(
            context as Activity,
            interHolder,
            10000,
            object :
                AdsInterCallBack {
                override fun onStartAction() {

                }

                override fun onEventClickAdClosed() {
                    loadInter(context, interHolder)
                    callback.onAdClosedOrFailed()
                }

                override fun onAdShowed() {
                    Log.d("===AdValue", "Show" )
                    Handler(Looper.getMainLooper()).postDelayed({
                        try {
                            AdmobRikatech.dismissAdDialog()
                        } catch (_: Exception) {

                        }
                    }, 800)
                }

                override fun onAdLoaded() {

                }

                override fun onAdFail(error: String?) {
                    Log.d("===Failed", error.toString())
                    loadInter(context, interHolder)
                    callback.onAdClosedOrFailed()
                }

                override fun onPaid(adValue: AdValue?, adUnitAds: String?) {
                    Log.d("===AdValue", "Inter: ${adValue?.currencyCode}|${adValue?.valueMicros}")
                }
            },
            true
        )
    }

    fun loadAndShowIntersial(
        activity: AppCompatActivity,
        inter: InterHolder,
        adListener: AdListener
    ) {
        AdmobRikatech.loadAndShowAdInterstitial(
            activity,
            inter,
            object : AdsInterCallBack {
                override fun onStartAction() {}
                override fun onEventClickAdClosed() {
                    adListener.onAdClosedOrFailed()
                }

                override fun onAdShowed() {}
                override fun onAdLoaded() {}
                override fun onAdFail(error: String) {
                    adListener.onAdClosedOrFailed()
                }

                override fun onPaid(adValue: AdValue, adUnitAds: String) {
                }
            },
            true
        )
    }

    fun loadNative(activity: Context, nativeHolder: NativeHolder) {
        AdmobRikatech.loadAndGetNativeAds(activity, nativeHolder, object :
            NativeAdCallback {
            override fun onLoadedAndGetNativeAd(ad: NativeAd?) {
            }

            override fun onNativeAdLoaded() {

            }

            override fun onAdFail(error: String) {
                Log.d(
                    "===AdsLoadsNative",
                    error.replace(":", "").replace(" ", "_").replace(".", "").replace("?", "")
                        .replace("!", "")
                )
            }

            override fun onAdPaid(adValue: AdValue?, adUnitAds: String?) {

            }
        })
    }

    fun loadNativeFullScreen(activity: Context, nativeHolder: NativeHolder) {
        AdmobRikatech.loadAndGetNativeFullScreenAds(
            activity,
            nativeHolder,
            MediaAspectRatio.SQUARE,
            object : AdmobRikatech.NativeAdCallbackNew {
                override fun onLoadedAndGetNativeAd(ad: NativeAd?) {
                }

                override fun onNativeAdLoaded() {

                }

                override fun onAdFail(error: String) {
                    Log.d(
                        "===AdsLoadsNative",
                        error.replace(":", "").replace(" ", "_").replace(".", "").replace("?", "")
                            .replace("!", "")
                    )
                }

                override fun onAdPaid(adValue: AdValue?, adUnitAds: String?) {

                }

                override fun onClickAds() {

                }
            })
    }

    fun showAdNativeFullScreen(activity: Activity, nativeAdContainer: ViewGroup, nativeHolder: NativeHolder) {
        if (!AdmobRikatech.isNetworkConnected(activity)) {
            nativeAdContainer.visibility = View.GONE
            return
        }
        AdmobRikatech.showNativeFullScreenAdsWithLayout(
            activity,
            nativeHolder,
            nativeAdContainer,
            R.layout.ad_unified,
            object : AdmobRikatech.AdsNativeCallBackAdmod {
                override fun NativeLoaded() {
                    Log.d("===NativeAds", "Native showed")
                    nativeAdContainer.visibility = View.VISIBLE
                }

                override fun NativeFailed(massage: String) {
                    Log.d("===NativeAds", "Native false")
                    nativeAdContainer.visibility = View.GONE
                }

                override fun onPaidNative(adValue: AdValue, adUnitAds: String) {

                }
            }
        )
    }

    fun showAdNative(activity: Activity, nativeAdContainer: ViewGroup, nativeHolder: NativeHolder) {
        if (!AdmobRikatech.isNetworkConnected(activity)) {
            nativeAdContainer.visibility = View.GONE
            return
        }
        AdmobRikatech.showNativeAdsWithLayout(
            activity,
            nativeHolder,
            nativeAdContainer,
            R.layout.ad_template_medium,
            GoogleENative.UNIFIED_MEDIUM,
            object : AdmobRikatech.AdsNativeCallBackAdmod {
                override fun NativeLoaded() {
                    Log.d("===NativeAds", "Native showed")
                    nativeAdContainer.visibility = View.VISIBLE
                }

                override fun NativeFailed(massage: String) {
                    Log.d("===NativeAds", "Native false")
                    nativeAdContainer.visibility = View.GONE
                }

                override fun onPaidNative(adValue: AdValue, adUnitAds: String) {

                }
            }
        )
    }

    @JvmStatic
    fun showAdBannerCollapsible(activity: Activity, id: String, view: ViewGroup, line: View) {
        if (AdmobRikatech.isNetworkConnected(activity)) {
            AdmobRikatech.loadAdBannerCollapsible(
                activity,
                id,
                CollapsibleBanner.BOTTOM,
                view,
                object : AdmobRikatech.BannerCollapsibleAdCallback {
                    override fun onClickAds() {

                    }

                    override fun onBannerAdLoaded(adSize: AdSize) {
                        view.visibility = View.VISIBLE
                        line.visibility = View.VISIBLE
                        val params: ViewGroup.LayoutParams = view.layoutParams
                        params.height = adSize.getHeightInPixels(activity)
                        view.layoutParams = params
                    }

                    override fun onAdFail(message: String) {
                        view.visibility = View.GONE
                        line.visibility = View.GONE
                    }

                    override fun onAdPaid(adValue: AdValue, mAdView: AdView) {
                        Log.d("===AdValue","Banner: ${adValue.currencyCode}|${adValue.valueMicros}")
                    }
                })
        } else {
            view.visibility = View.GONE
            line.visibility = View.GONE
        }
    }

    @JvmStatic
    fun showAdBanner(activity: Activity, id: String, view: ViewGroup, line: View) {
        if (AdmobRikatech.isNetworkConnected(activity)) {
            AdmobRikatech.loadAdBanner(activity, id, view, object :
                AdmobRikatech.BannerCallBack {
                override fun onClickAds() {

                }

                override fun onLoad() {
                    view.visibility = View.VISIBLE
                    line.visibility = View.VISIBLE
                }

                override fun onFailed(message: String) {
                    view.visibility = View.GONE
                    line.visibility = View.GONE
                }

                override fun onPaid(adValue: AdValue?, mAdView: AdView?) {
                    Log.d("===AdValue", "Banner: ${adValue?.currencyCode}|${adValue?.valueMicros}")
                }
            })
        } else {
            view.visibility = View.GONE
            line.visibility = View.GONE
        }
    }

    fun loadAndShowNativeFullScreen(
        activity: Activity,
        nativeAdContainer: ViewGroup,
        nativeHolder: NativeHolder
    ) {
        AdmobRikatech.loadAndShowNativeFullScreen(
            activity,
            nativeHolder.ads,
            nativeAdContainer,
            R.layout.ad_unified,
            MediaAspectRatio.SQUARE,
            object :
                NativeFullScreenCallBack {
                override fun onLoaded(nativeAd: NativeAd) {
                    Log.d("===native", "loadAndShowNativeFullScreen")
                }

                override fun onLoadFailed() {

                }

                override fun onPaidNative(adValue: AdValue, adUnitAds: String) {

                }

            })
    }

    fun loadReward(activity: Activity, interstitialAd: RewardedInterstitialHolder) {
        AdmobRikatech.loadAdInterstitialReward(activity, interstitialAd,
            object : AdLoadCallback {
                override fun onAdFail(message: String?) {}
                override fun onAdLoaded() {
                }
            })
    }

    fun showInterReward(
        activity: Activity,
        interstitialAd: RewardedInterstitialHolder,
        callback: AdListener
    ) {
        AdmobRikatech.showAdInterstitialRewardWithCallback(
            activity,
            interstitialAd,
            object : RewardAdCallback {
                override fun onAdClosed() {
                    callback.onAdClosedOrFailed()
                }

                override fun onAdShowed() {

                }

                override fun onAdFail(message: String?) {
                    callback.onAdClosedOrFailed()
                }

                override fun onEarned() {
                    Toast.makeText(
                        activity,
                        "onEarned",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onPaid(adValue: AdValue, adUnitAds: String?) {
                    Toast.makeText(
                        activity,
                        adValue.valueMicros.toString() + adValue.currencyCode,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    fun loadAndShowReward(
        activity: Activity,
        interstitialAd: RewardedInterstitialHolder,
        callback: AdListener
    ) {
        AdmobRikatech.loadAndShowAdRewardWithCallback(activity,interstitialAd,object : RewardAdCallback{
            override fun onAdClosed() {
                callback.onAdClosedOrFailed()
            }

            override fun onAdShowed() {
                Handler(Looper.getMainLooper()).postDelayed({
                    try {
                        AdmobRikatech.dismissAdDialog()
                    } catch (_: Exception) {

                    }
                }, 800)
            }

            override fun onAdFail(message: String?) {
                callback.onAdClosedOrFailed()
            }

            override fun onEarned() {
                Toast.makeText(
                    activity,
                    "onEarned",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onPaid(adValue: AdValue?, adUnitAds: String?) {
                Toast.makeText(
                    activity,
                    adValue?.valueMicros.toString() + adValue?.currencyCode,
                    Toast.LENGTH_SHORT
                ).show()
            }

        },true)
    }

    interface AdListener {
        fun onAdClosedOrFailed()
    }
}
