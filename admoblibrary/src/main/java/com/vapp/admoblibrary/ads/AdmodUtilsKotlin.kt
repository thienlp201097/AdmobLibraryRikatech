//package com.vapp.admoblibrary.ads
//
//import android.annotation.SuppressLint
//import android.app.Activity
//import android.app.ProgressDialog
//import android.content.Context
//import android.net.ConnectivityManager
//import android.os.Handler
//import android.provider.Settings
//import android.util.Log
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.ImageView
//import android.widget.RatingBar
//import android.widget.TextView
//import androidx.lifecycle.Lifecycle
//import androidx.lifecycle.ProcessLifecycleOwner
//import com.ads.google.admobads.admobnative.GoogleENative
//import com.google.android.gms.ads.*
//import com.google.android.gms.ads.initialization.InitializationStatus
//import com.google.android.gms.ads.interstitial.InterstitialAd
//import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
//import com.google.android.gms.ads.nativead.MediaView
//import com.google.android.gms.ads.nativead.NativeAd
//import com.google.android.gms.ads.nativead.NativeAdOptions
//import com.google.android.gms.ads.nativead.NativeAdView
//import com.google.android.gms.ads.rewarded.RewardedAd
//import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
//import java.security.MessageDigest
//import java.security.NoSuchAlgorithmException
//import java.util.*
//import com.vapp.admoblibrary.R
//
//
//class AdmodUtilsKotlin {
//    var dialog: ProgressDialog? = null
//    var lastTimeShowInterstitial: Long = 0
//    var isAdShowing = false
//    var isShowAds = true
//    var isTesting = false
//    var testDevices: MutableList<String> = ArrayList()
//
//    @Volatile
//    public var INSTANCE: AdmodUtilsKotlin? = null
//
//    @Synchronized
//    fun getInstance(): AdmodUtilsKotlin? {
//        if (INSTANCE == null) {
//            INSTANCE = AdmodUtilsKotlin()
//        }
//        return INSTANCE
//    }
//
//
//    fun initAdmob(
//        context: Context,
//        isDebug: Boolean,
//        isAddDeviceTest: Boolean,
//        isEnableAds: Boolean
//    ) {
//        if (!isEnableAds) {
//            isShowAds = false
//        }
//        MobileAds.initialize(
//            context
//        ) { initializationStatus: InitializationStatus? -> }
//        initListIdTest()
//        if (isAddDeviceTest) {
//            testDevices.add(getDeviceID(context))
//        }
//        val requestConfiguration = RequestConfiguration.Builder()
//            .setTestDeviceIds(testDevices)
//            .build()
//        MobileAds.setRequestConfiguration(requestConfiguration)
//        initAdRequest()
//        isTesting = if (isDebug) {
//            true
//        } else {
//            false
//        }
//    }
//
//    var adRequest: AdRequest? = null
//
//    // get AdRequest
//    fun initAdRequest() {
//        adRequest = AdRequest.Builder()
//            .setHttpTimeoutMillis(5000)
//            .build()
//    }
//
//    fun initListIdTest() {
//        testDevices.add("3C94990AA9A387A256D3B2BBBFEA51EA")
//        testDevices.add("6F599887BC401CFB1C7087F15D7C0834")
//        testDevices.add("B543DCF2C7591C7FB8B52A3A1E7138F6")
//        testDevices.add("8619926A823916A224795141B93B7E0B")
//        testDevices.add("6399D5AEE5C75205B6C0F6755365CF21")
//        testDevices.add("2E379568A9F147A64B0E0C9571DE812D")
//        testDevices.add("A0518C6FA4396B91F82B9656DE83AFC7")
//        testDevices.add("C8EEFFC32272E3F1018FC72ECBD46F0C")
//        testDevices.add("284A7F7624F1131E7341ECDCBBCDF9A8")
//        testDevices.add("FEECD9793CCCE1E0FF8D392B0DB65559")
//        testDevices.add("D34AE6EC4CBA619D6243B03D4E31EED6")
//        testDevices.add("25F9EEACB11D46869D2854923615D839")
//        testDevices.add("A5CB09DBBE486E3421502DFF53070339")
//        testDevices.add("5798E06F645D797640A9C4B90B6CBEA7")
//        testDevices.add("E91FD94E971864C3880FB434D1C39A03")
//        testDevices.add("50ACF2DAA0884FF8B08F7C823E046DEA")
//        testDevices.add("97F07D4A6D0145F9DB7114B63D3D8E9B")
//        testDevices.add("4C96668EC6F204034D0CDCE1B94A4E65")
//        testDevices.add("00A52C89E14694316247D3CA3DF19F6B")
//        testDevices.add("C38A7BF0A80E31BD6B76AF6D0C1EE4A1")
//        testDevices.add("CE604BDCEFEE2B9125CCFFC53E96022E")
//        testDevices.add("39D7026016640CEA1502836C6EF3776D")
//        testDevices.add("A99C99C378EE9BDE5D3DE404D3A4A812")
//        testDevices.add("EB28F4CCC32F14DC98068A063B97E6CE")
//    }
//
//
//    //check open network
//    fun isNetworkConnected(context: Context): Boolean {
//        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
//    }
//
//
//    fun loadAdBanner(context: Context, bannerId: String?, viewGroup: ViewGroup) {
//        var bannerId = bannerId
//        if (!isShowAds) {
//            viewGroup.visibility = View.GONE
//            return
//        }
//        val mAdView = AdView(context)
//        if (isTesting) {
//            bannerId = context.getString(R.string.ads_admob_banner_id)
//        }
//        mAdView.adUnitId = bannerId
//        mAdView.adSize = AdSize.SMART_BANNER
//        viewGroup.removeAllViews()
//        viewGroup.addView(mAdView)
//        mAdView.loadAd(adRequest)
//        Log.e(" Admod", "loadAdBanner")
//    }
//
//    // ads native
//    @SuppressLint("StaticFieldLeak")
//    fun loadNativeAds(activity: Activity, s: String?, viewGroup: ViewGroup, size: GoogleENative) {
//        var s = s
//        if (!isShowAds) {
//            viewGroup.visibility = View.GONE
//            return
//        }
//        val adLoader: AdLoader
//        if (isTesting) {
//            s = activity.getString(R.string.ads_admob_native_id)
//        }
//        adLoader = AdLoader.Builder(activity, s)
//            .forNativeAd { nativeAd ->
//                var id = 0
//                id = if (size === GoogleENative.UNIFIED_MEDIUM) {
//                    R.layout.ad_unified_medium
//                } else {
//                    R.layout.ad_unified_small
//                }
//                val adView = activity.layoutInflater
//                    .inflate(id, null) as NativeAdView
//               populateNativeAdView(nativeAd, adView, size)
//                viewGroup.removeAllViews()
//                viewGroup.addView(adView)
//                viewGroup.visibility = View.VISIBLE
//            }
//            .withAdListener(object : AdListener() {
//                override fun onAdFailedToLoad(adError: LoadAdError) {}
//            })
//            .withNativeAdOptions(NativeAdOptions.Builder().build()).build()
//        adLoader.loadAd(adRequest)
//        Log.e(" Admod", "loadAdNativeAds")
//    }
//
//    private fun populateNativeAdView(
//        nativeAd: NativeAd,
//        adView: NativeAdView,
//        size: GoogleENative
//    ) {
//        adView.findViewById<MediaView>(R.id.ad_media)?.let {
//            adView.mediaView = it
//        }
//        adView.findViewById<TextView>(R.id.ad_headline)?.let {
//            adView.headlineView = it
//        }
//        adView.findViewById<TextView>(R.id.ad_body)?.let {
//            adView.bodyView = it
//        }
//        adView.findViewById<Button>(R.id.ad_call_to_action)?.let {
//            adView.callToActionView = it
//        }
//        adView.findViewById<ImageView>(R.id.ad_app_icon)?.let {
//            adView.iconView = it
//        }
//        adView.findViewById<RatingBar>(R.id.ad_stars)?.let {
//            adView.starRatingView = it
//        }
//
//        if (size == GoogleENative.UNIFIED_MEDIUM) {
//            adView.mediaView.setMediaContent(nativeAd.mediaContent)
//        }
//
//        (adView.headlineView as TextView).text = nativeAd.headline
//        if (nativeAd.body == null) {
//            adView.bodyView.visibility = View.INVISIBLE
//        } else {
//            adView.bodyView.visibility = View.VISIBLE
//            (adView.bodyView as TextView).text = nativeAd.body
//        }
//        if (nativeAd.callToAction == null) {
//            adView.callToActionView.visibility = View.INVISIBLE
//        } else {
//            adView.callToActionView.visibility = View.VISIBLE
//            (adView.callToActionView as Button).text = nativeAd.callToAction
//        }
//        if (nativeAd.icon == null) {
//            adView.iconView.visibility = View.GONE
//        } else {
//            (adView.iconView as ImageView).setImageDrawable(
//                nativeAd.icon.drawable
//            )
//            adView.iconView.visibility = View.VISIBLE
//        }
//        if (nativeAd.starRating == null) {
//            adView.starRatingView.visibility = View.INVISIBLE
//        } else {
//            (adView.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
//            adView.starRatingView.visibility = View.VISIBLE
//        }
//        adView.setNativeAd(nativeAd)
//        val vc = nativeAd.mediaContent.videoController
//        if (vc.hasVideoContent()) {
//            vc.videoLifecycleCallbacks = object : VideoController.VideoLifecycleCallbacks() {
//                override fun onVideoEnd() {
//                    super.onVideoEnd()
//                }
//            }
//        }
//    }
//
//    var mRewardedAd: RewardedAd? = null
//
//    fun loadAndShowAdRewardWithCallback(
//        activity: Activity,
//        admobId: String?,
//        adCallback2: AdCallback,
//        enableLoadingDialog: Boolean
//    ) {
//        var admobId = admobId
//        if (!isShowAds) {
//            adCallback2.onAdClosed()
//            return
//        }
//        if (isTesting) {
//            admobId = activity.getString(R.string.ads_admob_reward_id)
//        }
//        if (enableLoadingDialog) {
//            dialog = ProgressDialog(activity, R.style.AppCompatAlertDialogStyle)
//            dialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
//            dialog!!.setTitle("Loading")
//            dialog!!.setMessage("Loading ads. Please wait...")
//            dialog!!.isIndeterminate = true
//            dialog!!.setCanceledOnTouchOutside(false)
//            dialog!!.show()
//        }
//        RewardedAd.load(activity, admobId,
//            adRequest, object : RewardedAdLoadCallback() {
//                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
//                    // Handle the error.
//                    mRewardedAd = null
//                    if (dialog != null) {
//                        dialog!!.dismiss()
//                    }
//                    adCallback2.onAdFail()
//                }
//
//                override fun onAdLoaded(rewardedAd: RewardedAd) {
//                    mRewardedAd = rewardedAd
//                    if (dialog != null) {
//                        dialog!!.dismiss()
//                    }
//                    if (mRewardedAd != null) {
//                        mRewardedAd!!.setFullScreenContentCallback(object :
//                            FullScreenContentCallback() {
//                            override fun onAdShowedFullScreenContent() {
//                                // Called when ad is shown.
//                            }
//
//                            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
//                                // Called when ad fails to show.
//                                if (dialog != null) {
//                                    dialog!!.dismiss()
//                                }
//                                adCallback2.onAdFail()
//                                mRewardedAd = null
//                            }
//
//                            override fun onAdDismissedFullScreenContent() {
//                                // Called when ad is dismissed.
//                                // Set the ad reference to null so you don't show the ad a second time.
//                                mRewardedAd = null
//                                if (dialog != null) {
//                                    dialog!!.dismiss()
//                                }
//                            }
//                        })
//                        mRewardedAd!!.show(activity) { // Handle the reward.
//                            adCallback2.onAdClosed()
//                            if (AppOpenManager.getInstance().isInitialized) {
//                                AppOpenManager.getInstance().enableAppResume()
//                            }
//                        }
//                    } else {
//                        if (dialog != null) {
//                            dialog!!.dismiss()
//                        }
//                        adCallback2.onAdFail()
//                    }
//                }
//            })
//    }
//
//    //inter ads
//    var mInterstitialAd: InterstitialAd? = null
//
//    fun loadAdInterstitial(context: Context, s: String?, enableLoadingDialog: Boolean) {
//        var s = s
//        if (isTesting) {
//            s = context.getString(R.string.ads_admob_inter_id)
//        }
//        if (enableLoadingDialog) {
//            dialog = ProgressDialog(context, R.style.AppCompatAlertDialogStyle)
//            dialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
//            dialog!!.setTitle("Loading")
//            dialog!!.setMessage("Loading ads. Please wait...")
//            dialog!!.isIndeterminate = true
//            dialog!!.setCanceledOnTouchOutside(false)
//            dialog!!.show()
//        }
//        InterstitialAd.load(context, s, adRequest, object : InterstitialAdLoadCallback() {
//            override fun onAdLoaded(interstitialAd: InterstitialAd) {
//                super.onAdLoaded(interstitialAd)
//                mInterstitialAd = interstitialAd
//            }
//
//            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
//                super.onAdFailedToLoad(loadAdError)
//                mInterstitialAd = null
//            }
//        })
//    }
//
//    fun showAdInterstitialWithCallback(
//        activity: Activity?,
//        adCallback: AdCallback,
//        limitTime: Int
//    ) {
//        if (!isShowAds) {
//            adCallback.onAdClosed()
//            return
//        }
//        val currentTime = getCurrentTime()
//        if (mInterstitialAd != null) {
//            if (currentTime - lastTimeShowInterstitial >= limitTime) {
//                mInterstitialAd!!.setFullScreenContentCallback(object :
//                    FullScreenContentCallback() {
//                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
//                        Log.e(" Admod", "showAdInterstitial")
//                        Log.e(" Admod", "errorCodeAds:" + adError.message)
//                        Log.e(" Admod", "errorCodeAds:" + adError.cause)
//                        if (dialog != null) {
//                            dialog!!.dismiss()
//                        }
//                        adCallback.onAdClosed()
//                        if (AppOpenManager.getInstance().isInitialized) {
//                            AppOpenManager.getInstance().enableAppResume()
//                        }
//                    }
//
//                    override fun onAdShowedFullScreenContent() {
//                        super.onAdShowedFullScreenContent()
//                        if (dialog != null) {
//                            dialog!!.dismiss()
//                        }
//                    }
//
//                    override fun onAdDismissedFullScreenContent() {
//                        if (dialog != null) {
//                            dialog!!.dismiss()
//                        }
//                        adCallback.onAdClosed()
//                        if (AppOpenManager.getInstance().isInitialized) {
//                            AppOpenManager.getInstance().enableAppResume()
//                        }
//                    }
//
//                    override fun onAdImpression() {
//                        super.onAdImpression()
//                    }
//                })
//            } else {
//                if (dialog != null) {
//                    dialog!!.dismiss()
//                }
//                adCallback.onAdClosed()
//                if (AppOpenManager.getInstance().isInitialized) {
//                    AppOpenManager.getInstance().enableAppResume()
//                }
//            }
//        }
//        if (mInterstitialAd != null) {
//            if (ProcessLifecycleOwner.get().lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
//                try {
//                    if (dialog != null && dialog!!.isShowing) dialog!!.dismiss()
//                } catch (e: Exception) {
//                    dialog = null
//                    dialog?.dismiss()
//                    e.printStackTrace()
//                }
//                Handler().postDelayed({
//                    if (AppOpenManager.getInstance().isInitialized) {
//                        AppOpenManager.getInstance().disableAppResume()
//                    }
//                    mInterstitialAd!!.show(activity)
//                }, 800)
//            }
//        }
//    }
//
//    fun loadAndShowAdInterstitialWithCallback(
//        activity: Activity,
//        admobId: String?,
//        limitTime: Int,
//        adCallback: AdCallback,
//        enableLoadingDialog: Boolean
//    ) {
//        var admobId = admobId
//        if (!isShowAds) {
//            adCallback.onAdClosed()
//            return
//        }
//        val currentTime = getCurrentTime()
//        if (currentTime - lastTimeShowInterstitial >= limitTime) {
//            if (isTesting) {
//                admobId = activity.getString(R.string.ads_admob_inter_id)
//            }
//            if (enableLoadingDialog) {
//                dialog = ProgressDialog(activity, R.style.AppCompatAlertDialogStyle)
//                dialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
//                dialog!!.setTitle("Loading")
//                dialog!!.setMessage("Loading ads. Please wait...")
//                dialog!!.isIndeterminate = true
//                dialog!!.setCanceledOnTouchOutside(false)
//                dialog!!.show()
//            }
//            InterstitialAd.load(
//                activity,
//                admobId,
//                adRequest,
//                object : InterstitialAdLoadCallback() {
//                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
//                        super.onAdLoaded(interstitialAd)
//                        mInterstitialAd = interstitialAd
//                        if (mInterstitialAd != null) {
//                            mInterstitialAd!!.setFullScreenContentCallback(object :
//                                FullScreenContentCallback() {
//                                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
//                                    adCallback.onAdClosed()
//                                    if (dialog != null) {
//                                        dialog!!.dismiss()
//                                    }
//                                    isAdShowing = false
//                                    if (AppOpenManager.getInstance().isInitialized) {
//                                        AppOpenManager.getInstance().enableAppResume()
//                                    }
//                                }
//
//                                override fun onAdDismissedFullScreenContent() {
//                                    adCallback.onAdClosed()
//                                    if (dialog != null) {
//                                        dialog!!.dismiss()
//                                    }
//                                    isAdShowing = false
//                                    if (AppOpenManager.getInstance().isInitialized) {
//                                        AppOpenManager.getInstance().enableAppResume()
//                                    }
//                                }
//                            })
//                            if (dialog != null) {
//                                dialog!!.dismiss()
//                            }
//                            if (ProcessLifecycleOwner.get().lifecycle.currentState.isAtLeast(
//                                    Lifecycle.State.RESUMED
//                                )
//                            ) {
//                                try {
//                                    if (dialog != null && dialog!!.isShowing) dialog!!.dismiss()
//                                } catch (e: Exception) {
//                                    dialog = null
//                                    dialog?.dismiss()
//                                    e.printStackTrace()
//                                }
//                                Handler().postDelayed({
//                                    if (AppOpenManager.getInstance().isInitialized) {
//                                        AppOpenManager.getInstance().disableAppResume()
//                                    }
//                                    mInterstitialAd!!.show(activity)
//                                }, 800)
//                            }
//                        } else {
//                            if (dialog != null) {
//                                dialog!!.dismiss()
//                            }
//                            adCallback.onAdFail()
//                        }
//                    }
//
//                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
//                        super.onAdFailedToLoad(loadAdError)
//                        mInterstitialAd = null
//                        if (dialog != null) {
//                            dialog!!.dismiss()
//                        }
//                        adCallback.onAdFail()
//                    }
//                })
//        } else {
//            adCallback.onAdClosed()
//            if (dialog != null) {
//                dialog!!.dismiss()
//            }
//        }
//    }
//
//    private fun getCurrentTime(): Long {
//        return System.currentTimeMillis()
//    }
//
//    fun getDeviceID(context: Context): String {
//        val android_id = Settings.Secure.getString(
//            context.contentResolver,
//            Settings.Secure.ANDROID_ID
//        )
//        return md5(android_id).toUpperCase()
//    }
//
//    fun md5(s: String): String {
//        try {
//            // Create MD5 Hash
//            val digest = MessageDigest.getInstance("MD5")
//            digest.update(s.toByteArray())
//            val messageDigest = digest.digest()
//
//            // Create Hex String
//            val hexString = StringBuffer()
//            for (i in messageDigest.indices) hexString.append(
//                Integer.toHexString(
//                    0xFF and messageDigest[i]
//                        .toInt()
//                )
//            )
//            return hexString.toString()
//        } catch (e: NoSuchAlgorithmException) {
//            e.printStackTrace()
//        }
//        return ""
//    }
//}