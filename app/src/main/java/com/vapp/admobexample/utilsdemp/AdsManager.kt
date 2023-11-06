package com.vapp.admobexample.utilsdemp

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
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
import com.vapp.admoblibrary.ads.model.AppOpenAppHolder
import com.vapp.admoblibrary.ads.model.BannerHolder
import com.vapp.admoblibrary.ads.model.InterHolder
import com.vapp.admoblibrary.ads.model.NativeHolder
import com.vapp.admoblibrary.ads.nativefullscreen.NativeFullScreenCallBack
import com.vapp.admoblibrary.ads.remote.BannerPlugin
import com.vapp.admoblibrary.ads.remote.BannerRemoteConfig

object AdsManager {
    var interAds1: InterstitialAd? = null
    val mutable_inter1: MutableLiveData<InterstitialAd> = MutableLiveData()
    var check_inter1 = false

    var nativeHolder = NativeHolder(
        ""
    )
    var bannerHolder = BannerHolder("", "")
    var aoaHolder = AppOpenAppHolder("", "")
    var interholder = InterHolder("")
    fun loadAndShowBannerRemote(activity: Activity, id : String ,bannerConfig: BannerPlugin.BannerConfig?, view: ViewGroup, line: View){
        BannerPlugin(activity, view,id,bannerConfig,object : BannerRemoteConfig{
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

    fun loadAndShowNative(activity: Activity, nativeAdContainer: ViewGroup, nativeHolder: NativeHolder){
        if (!AdmodUtils.isNetworkConnected(activity)) {
            nativeAdContainer.visibility = View.GONE
            return
        }
        AdmodUtils.loadAndShowNativeAdsWithLayoutMultiAds(activity,nativeHolder, nativeAdContainer,R.layout.ad_template_medium,GoogleENative.UNIFIED_MEDIUM,object : NativeAdCallback{
            override fun onLoadedAndGetNativeAd(ad: NativeAd?) {
            }

            override fun onNativeAdLoaded() {
                Log.d("===nativeload","true")
                nativeAdContainer.visibility = View.VISIBLE
            }

            override fun onAdFail(error: String?) {
                nativeAdContainer.visibility = View.GONE
            }

            override fun onAdPaid(adValue: AdValue?, adUnitAds: String?) {
                Log.d("===AdValue","Native: ${adValue?.currencyCode}|${adValue?.valueMicros}")
            }
        })
    }

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

                }

                override fun onEventClickAdClosed() {
                    loadInter(context, interHolder)
                    callback.onAdClosedOrFailed()
                }

                override fun onAdShowed() {
                    Log.d("===AdValue", "Show" )
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
                    loadInter(context, interHolder)
                    callback.onAdClosedOrFailed()
                }

                override fun onPaid(adValue: AdValue?, adUnitAds: String?) {
                    Log.d("===AdValue","Inter: ${adValue?.currencyCode}|${adValue?.valueMicros}")
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

                override fun onPaid(adValue: AdValue?, adUnitAds: String?) {
                    Toast.makeText(activity, "${adValue?.currencyCode}|${adValue?.valueMicros}", Toast.LENGTH_SHORT).show()
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
                Log.d("===AdsLoadsNative",
                    error.replace(":", "").replace(" ", "_").replace(".", "").replace("?", "")
                        .replace("!", "")
                )
            }

            override fun onAdPaid(adValue: AdValue?, adUnitAds: String?) {

            }
        })
    }

    fun loadNativeFullScreen(activity: Context, nativeHolder: NativeHolder) {
        AdmodUtils.loadAndGetNativeFullScreenAds(activity, nativeHolder, object : NativeAdCallback {
            override fun onLoadedAndGetNativeAd(ad: NativeAd?) {
            }

            override fun onNativeAdLoaded() {

            }

            override fun onAdFail(error: String) {
                Log.d("===AdsLoadsNative",
                    error.replace(":", "").replace(" ", "_").replace(".", "").replace("?", "")
                        .replace("!", "")
                )
            }

            override fun onAdPaid(adValue: AdValue?, adUnitAds: String?) {

            }
        })
    }

    fun showAdNativeFullScreen(activity: Activity, nativeAdContainer: ViewGroup, nativeHolder: NativeHolder) {
        if (!AdmodUtils.isNetworkConnected(activity)) {
            nativeAdContainer.visibility = View.GONE
            return
        }
        AdmodUtils.showNativeFullScreenAdsWithLayout(activity, nativeHolder, nativeAdContainer, R.layout.ad_unified, object : AdmodUtils.AdsNativeCallBackAdmod {
                override fun NativeLoaded() {
                    Log.d("===NativeAds", "Native showed")
                    nativeAdContainer.visibility = View.VISIBLE
                }

                override fun NativeFailed() {
                    Log.d("===NativeAds", "Native false")
                    nativeAdContainer.visibility = View.GONE
                }

            override fun onPaidNative(adValue: AdValue, adUnitAds: String) {

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

            override fun onPaidNative(adValue: AdValue, adUnitAds: String) {

            }
        }
        )
    }

    @JvmStatic
    fun showAdBannerCollapsible(activity: Activity, bannerHolder: BannerHolder, view: ViewGroup, line: View) {
        if (AdmodUtils.isNetworkConnected(activity)) {
            AdmodUtils.loadAdBannerCollapsibleMultiAds(
                activity,
                bannerHolder,
                CollapsibleBanner.BOTTOM,
                view,
                object : AdmodUtils.BannerCollapsibleAdCallback {
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
    fun showAdBanner(activity: Activity, bannerHolder: BannerHolder, view: ViewGroup, line: View) {
        if (AdmodUtils.isNetworkConnected(activity)) {
            AdmodUtils.loadAdBannerMultiAds(activity, bannerHolder, view, object :
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
                    Log.d("===AdValue","Banner: ${adValue?.currencyCode}|${adValue?.valueMicros}")
                }
            })
        } else {
            view.visibility = View.GONE
            line.visibility = View.GONE
        }
    }

    fun loadAndShowNativeFullScreen(activity: Activity, nativeAdContainer: ViewGroup, nativeHolder: NativeHolder){

        AdmodUtils.loadAndShowNativeFullScreen(activity,nativeHolder.ads,nativeAdContainer,R.layout.ad_unified,object : NativeFullScreenCallBack{
            override fun onLoaded(nativeAd: NativeAd) {
                Log.d("===native","loadAndShowNativeFullScreen")
            }

            override fun onLoadFailed() {

            }

            override fun onPaidNative(adValue: AdValue, adUnitAds: String) {

            }

        })
    }

    interface AdListener {
        fun onAdClosedOrFailed()
    }
}
