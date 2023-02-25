package com.vapp.admoblibrary.ads;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.vapp.admoblibrary.BuildConfig;
import com.vapp.admoblibrary.ads.admobnative.enumclass.CollapsibleBanner;
import com.vapp.admoblibrary.ads.admobnative.enumclass.GoogleEBanner;
import com.vapp.admoblibrary.ads.admobnative.enumclass.GoogleENative;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.vapp.admoblibrary.R;
import com.vapp.admoblibrary.ads.admobnative.NativeFunc;
import com.vapp.admoblibrary.ads.model.AdUnitListModel;
import com.vapp.admoblibrary.ads.model.BannerAdCallback;
import com.vapp.admoblibrary.utils.DialogCallback;
import com.vapp.admoblibrary.utils.DialogType;
import com.vapp.admoblibrary.utils.SweetAlert.SweetAlertDialog;
import com.vapp.admoblibrary.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AdmodUtils {
    //Dialog loading
    public SweetAlertDialog dialog;
    public Dialog dialogFullScreen;
    // Biến check lần cuối hiển thị quảng cáo
    public long lastTimeShowInterstitial = 0;
    // Timeout init admob
    public int timeOut = 0;
    //Check quảng cáo đang show hay không
    public boolean isAdShowing = false;
    //Ẩn hiện quảng cáo
    public boolean isShowAds = true;
    //Dùng ID Test để hiển thị quảng cáo
    public boolean isTesting = false;
    //List device test
    public List<String> testDevices = new ArrayList<>();
    //INSTANCE AdmodUtils
    private static volatile AdmodUtils INSTANCE;
    //Reward Ads
    public RewardedAd mRewardedAd = null;
    public InterstitialAd mInterstitialAd;

    ShimmerFrameLayout shimmerFrameLayout;
    //id thật
    public String idIntersitialReal;

    public static synchronized AdmodUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AdmodUtils();
        }
        return INSTANCE;
    }

    //Hàm Khởi tạo admob
    public void initAdmob(Context context, int timeout, boolean isDebug, boolean isEnableAds) {
        timeOut = timeout;
        if (timeOut < 5000 && timeout != 0) {
            Toast.makeText(context, "Nên để limit time ~10000", Toast.LENGTH_LONG).show();
        }

        if (timeout > 0) {
            timeOut = timeout;
        } else {
            timeOut = 10000;
        }

        if (isDebug) {
            isTesting = true;
        } else {
            isTesting = false;
        }

        isShowAds = isEnableAds;

        MobileAds.initialize(context, initializationStatus -> {
        });

        initListIdTest();
        RequestConfiguration requestConfiguration
                = new RequestConfiguration.Builder()
                .setTestDeviceIds(testDevices)
                .build();
        MobileAds.setRequestConfiguration(requestConfiguration);
        initAdRequest(timeout);


    }

    public AdRequest adRequest;

    // get AdRequest
    public void initAdRequest(int timeOut) {
        adRequest = new AdRequest.Builder()
                .setHttpTimeoutMillis(timeOut)
                .build();
    }

    public void initListIdTest() {
        testDevices.add("727D4F658B63BDFA0EFB164261AAE54");
        testDevices.add("3FA34D6F6B2DCF88DED51A6AF263E3F0");
        testDevices.add("482996BF6946FBE1B9FFD3975144D084");
        testDevices.add("8619926A823916A224795141B93B7E0B");
        testDevices.add("6399D5AEE5C75205B6C0F6755365CF21");
        testDevices.add("2E379568A9F147A64B0E0C9571DE812D");
        testDevices.add("A0518C6FA4396B91F82B9656DE83AFC7");
        testDevices.add("C8EEFFC32272E3F1018FC72ECBD46F0C");
        testDevices.add("284A7F7624F1131E7341ECDCBBCDF9A8");
        testDevices.add("FEECD9793CCCE1E0FF8D392B0DB65559");
        testDevices.add("D34AE6EC4CBA619D6243B03D4E31EED6");
        testDevices.add("25F9EEACB11D46869D2854923615D839");
        testDevices.add("A5CB09DBBE486E3421502DFF53070339");
        testDevices.add("5798E06F645D797640A9C4B90B6CBEA7");
        testDevices.add("E91FD94E971864C3880FB434D1C39A03");
        testDevices.add("50ACF2DAA0884FF8B08F7C823E046DEA");
        testDevices.add("97F07D4A6D0145F9DB7114B63D3D8E9B");
        testDevices.add("4C96668EC6F204034D0CDCE1B94A4E65");
        testDevices.add("00A52C89E14694316247D3CA3DF19F6B");
        testDevices.add("C38A7BF0A80E31BD6B76AF6D0C1EE4A1");
        testDevices.add("CE604BDCEFEE2B9125CCFFC53E96022E");
        testDevices.add("39D7026016640CEA1502836C6EF3776D");
        testDevices.add("A99C99C378EE9BDE5D3DE404D3A4A812");
        testDevices.add("EB28F4CCC32F14DC98068A063B97E6CE");
        //Oneplus GM1910
        testDevices.add("D94D5042C9CC42DA75DCC0C4C233A500");
        //Redmi note 4
        testDevices.add("3FA34D6F6B2DCF88DED51A6AF263E3F0");
        //Galaxy M11
        testDevices.add("AF6ABEDE9EE7719295BF5E6F19A40452");
        //Samsung SM-G610F
        testDevices.add("2B018C52668CBA0B033F411955A5B561");
        //Realme RMX1851
        testDevices.add("39D7026016640CEA1502836C6EF3776D");
        //Redmi 5 Plus
        testDevices.add("CE604BDCEFEE2B9125CCFFC53E96022E");
        //Redmi 9A
        testDevices.add("13D67F452A299DB825A348917D52D640");
        //POCO X3
        testDevices.add("7D94825002E2407B75A9D5378194CFA9");
        //Galaxy A21S
        testDevices.add("98EFC23E56FA228C791F8C3AFBEE44D4");
        //Oppo CPH1825
        testDevices.add("805702C1D9D4FD957AFE14F3D69E79F7");
        //Xiaomi Redmi Note 7
        testDevices.add("9C62AAC36B9F23413AF4D66FE48F9E9B");

    }

    //check open network
    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void loadAdBanner(Activity activity, String bannerId, ViewGroup viewGroup) {

        if (!isShowAds || !isNetworkConnected(activity)) {
            viewGroup.setVisibility(View.GONE);
            return;
        }

        AdView mAdView = new AdView(activity);
        if (isTesting) {
            bannerId = activity.getString(R.string.test_ads_admob_banner_id);
        }
        mAdView.setAdUnitId(bannerId);
        AdSize adSize = getAdSize(activity);

        mAdView.setAdSize(adSize);
        viewGroup.removeAllViews();
        View tagView = activity.getLayoutInflater().inflate(R.layout.layoutbanner_loading, null, false);
        viewGroup.addView(tagView, 0);
        viewGroup.addView(mAdView, 1);
        shimmerFrameLayout = tagView.findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmer();


        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                shimmerFrameLayout.stopShimmer();
                viewGroup.removeView(tagView);
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Log.e(" Admod", "failloadbanner" + adError.getMessage());
                shimmerFrameLayout.stopShimmer();
                viewGroup.removeView(tagView);
            }

            @Override
            public void onAdOpened() {
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

        if (adRequest != null) {
            mAdView.loadAd(adRequest);
        }
        Log.e(" Admod", "loadAdBanner");
    }

    public void loadAdBannerCollapsible(Activity activity, String bannerId, CollapsibleBanner collapsibleBannersize, ViewGroup viewGroup, BannerAdCallback callback) {
        if (!isShowAds || !isNetworkConnected(activity)) {
            viewGroup.setVisibility(View.GONE);
            return;
        }
        AdView mAdView = new AdView(activity);
        if (isTesting) {
            bannerId = activity.getString(R.string.test_ads_admob_banner_id);
        }
        mAdView.setAdUnitId(bannerId);
        AdSize adSize = getAdSize(activity);
        mAdView.setAdSize(adSize);
        viewGroup.removeAllViews();
        View tagView = activity.getLayoutInflater().inflate(R.layout.layoutbanner_loading, null, false);
        viewGroup.addView(tagView, 0);
        viewGroup.addView(mAdView, 1);
        shimmerFrameLayout = tagView.findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmer();

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                shimmerFrameLayout.stopShimmer();
                viewGroup.removeView(tagView);
                callback.onBannerAdLoaded(adSize);
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Log.e(" Admod", "failloadbanner" + adError.getMessage());
                shimmerFrameLayout.stopShimmer();
                viewGroup.removeView(tagView);
                callback.onAdFail();
            }

            @Override
            public void onAdOpened() {
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
        Bundle extras = new Bundle();
        String anchored = "top";
        if (collapsibleBannersize == CollapsibleBanner.TOP) {
            anchored = "top";
        } else {
            anchored = "bottom";
        }
        extras.putString("collapsible", anchored);
        AdRequest adRequest2 = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, extras)
                .build();
        if (adRequest2 != null) {
            mAdView.loadAd(adRequest2);
        }

        Log.e(" Admod", "loadAdBanner");
    }

    private AdSize getAdSize(Activity context) {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }


    public void loadAndGetNativeAds(Context context, String s, NativeAdCallback adCallback) {
        if (!isShowAds || !isNetworkConnected(context)) {
            return;
        }
        AdLoader adLoader;
        if (isTesting) {
            s = context.getString(R.string.test_ads_admob_native_id);
        }

        adLoader = new AdLoader.Builder(context, s)
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {

                    @Override
                    public void onNativeAdLoaded(@NonNull @NotNull NativeAd nativeAd) {
                        adCallback.onLoadedAndGetNativeAd(nativeAd);
                        //viewGroup.setVisibility(View.VISIBLE);
                    }

                }).withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        Log.e("Admodfail", "onAdFailedToLoad" + adError.getMessage());
                        Log.e("Admodfail", "errorCodeAds" + adError.getCause());
                        adCallback.onAdFail();
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder().build()).build();

        if (adRequest != null) {
            adLoader.loadAd(adRequest);
        }
        Log.e("Admod", "loadAdNativeAds");
    }

    public void showNativeAdsWithLayout(Activity activity, NativeAd nativeAd, ViewGroup viewGroup, int layout, GoogleENative size, NativeAdCallback adCallback) {
        if (!isShowAds || !isNetworkConnected(activity)) {
            viewGroup.setVisibility(View.GONE);
            return;
        }
        if (shimmerFrameLayout != null) {
            shimmerFrameLayout.stopShimmer();
        }
        viewGroup.removeAllViews();

        if (nativeAd == null) {
            View tagView;

            if (size == GoogleENative.UNIFIED_MEDIUM) {
                tagView = activity.getLayoutInflater().inflate(R.layout.layoutnative_loading_medium, null, false);
            } else {
                tagView = activity.getLayoutInflater().inflate(R.layout.layoutnative_loading_small, null, false);
            }
            viewGroup.addView(tagView, 0);
            if (shimmerFrameLayout == null)
                shimmerFrameLayout = tagView.findViewById(R.id.shimmer_view_container);
            shimmerFrameLayout.startShimmer();
        } else {
            NativeAdView adView = (NativeAdView) activity.getLayoutInflater()
                    .inflate(layout, null);

            NativeFunc.Companion.populateNativeAdView(nativeAd, adView, GoogleENative.UNIFIED_MEDIUM);
            if (shimmerFrameLayout != null) {
                shimmerFrameLayout.stopShimmer();
            }
            viewGroup.addView(adView);
        }


    }

    // ads native
    public void loadAndShowNativeAdsWithLayout(Activity activity, String s, ViewGroup viewGroup, int layout, GoogleENative size, NativeAdCallback adCallback) {

        View tagView;
        if (size == GoogleENative.UNIFIED_MEDIUM) {
            tagView = activity.getLayoutInflater().inflate(R.layout.layoutnative_loading_medium, null, false);
        } else {
            tagView = activity.getLayoutInflater().inflate(R.layout.layoutnative_loading_small, null, false);
        }
        viewGroup.addView(tagView, 0);
        ShimmerFrameLayout shimmerFrameLayout = tagView.findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmer();

        if (!isShowAds || !isNetworkConnected(activity)) {
            viewGroup.setVisibility(View.GONE);
            return;
        }

        AdLoader adLoader;
        if (isTesting) {
            s = activity.getString(R.string.test_ads_admob_native_id);
        }

        adLoader = new AdLoader.Builder(activity, s)
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {

                    @Override
                    public void onNativeAdLoaded(@NonNull @NotNull NativeAd nativeAd) {
                        adCallback.onNativeAdLoaded();

                        NativeAdView adView = (NativeAdView) activity.getLayoutInflater()
                                .inflate(layout, null);

                        NativeFunc.Companion.populateNativeAdView(nativeAd, adView, GoogleENative.UNIFIED_MEDIUM);

                        shimmerFrameLayout.stopShimmer();
                        viewGroup.removeAllViews();
                        viewGroup.addView(adView);
                        //viewGroup.setVisibility(View.VISIBLE);
                    }

                }).withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        Log.e("Admodfail", "onAdFailedToLoad" + adError.getMessage());
                        Log.e("Admodfail", "errorCodeAds" + adError.getCause());
                        shimmerFrameLayout.stopShimmer();
                        viewGroup.removeAllViews();
                        adCallback.onAdFail();
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder().build()).build();

        if (adRequest != null) {
            adLoader.loadAd(adRequest);
        }
        Log.e("Admod", "loadAdNativeAds");
    }


    // ads native
    @SuppressLint("StaticFieldLeak")
    public void loadAndShowNativeAds(Activity activity, String s, ViewGroup viewGroup, GoogleENative size, NativeAdCallback adCallback) {
        View tagView;
        if (size == GoogleENative.UNIFIED_MEDIUM) {
            tagView = activity.getLayoutInflater().inflate(R.layout.layoutnative_loading_medium, null, false);
        } else {
            tagView = activity.getLayoutInflater().inflate(R.layout.layoutnative_loading_small, null, false);
        }
        viewGroup.addView(tagView, 0);
        ShimmerFrameLayout shimmerFrameLayout = tagView.findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmer();
        if (!isShowAds || !isNetworkConnected(activity)) {
            viewGroup.setVisibility(View.GONE);
            return;
        }

        AdLoader adLoader;
        if (isTesting) {
            s = activity.getString(R.string.test_ads_admob_native_id);
        }

        adLoader = new AdLoader.Builder(activity, s)
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {

                    @Override
                    public void onNativeAdLoaded(@NonNull @NotNull NativeAd nativeAd) {
                        adCallback.onNativeAdLoaded();
                        int id = 0;
                        if (size == GoogleENative.UNIFIED_MEDIUM) {
                            id = R.layout.ad_unified_medium;
                        } else {
                            id = R.layout.ad_unified_small;
                        }

                        NativeAdView adView = (NativeAdView) activity.getLayoutInflater()
                                .inflate(id, null);

                        NativeFunc.Companion.populateNativeAdView(nativeAd, adView, size);
                        shimmerFrameLayout.stopShimmer();
                        viewGroup.removeAllViews();
                        viewGroup.addView(adView);
                        //viewGroup.setVisibility(View.VISIBLE);
                    }

                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        Log.e("Admodfail", "onAdFailedToLoad" + adError.getMessage());
                        Log.e("Admodfail", "errorCodeAds" + adError.getCause());
                        shimmerFrameLayout.stopShimmer();
                        viewGroup.removeAllViews();
                        adCallback.onAdFail();
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder().build()).build();

        if (adRequest != null) {
            adLoader.loadAd(adRequest);
        }
        Log.e("Admod", "loadAdNativeAds");
    }

    public void loadAndShowAdRewardWithCallback(Activity activity, String admobId, RewardAdCallback adCallback2, boolean enableLoadingDialog) {
        AdmodUtils.getInstance().mInterstitialAd = null;
        AdmodUtils.getInstance().isAdShowing = false;
        if (!isShowAds || !isNetworkConnected(activity)) {
            adCallback2.onAdClosed();
            return;
        }
        if (adRequest == null) {
            initAdRequest(timeOut);
        }

        if (isTesting) {
            admobId = activity.getString(R.string.test_ads_admob_reward_id);
        }
        if (enableLoadingDialog) {

            dialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
            dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            dialog.setTitleText("Loading ads. Please wait...");
            dialog.setCancelable(false);
            dialog.show();
        }

        isAdShowing = false;
        if (AppOpenManager.getInstance().isInitialized()) {
            AppOpenManager.getInstance().isAppResumeEnabled = false;
        }


        RewardedAd.load(activity, admobId,
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        mRewardedAd = null;
                        adCallback2.onAdFail();
                        dismissAdDialog();
                        if (AppOpenManager.getInstance().isInitialized()) {
                            AppOpenManager.getInstance().isAppResumeEnabled = true;
                        }
                        AdmodUtils.getInstance().isAdShowing = false;
                        Log.e("Admodfail", "onAdFailedToLoad" + loadAdError.getMessage());
                        Log.e("Admodfail", "errorCodeAds" + loadAdError.getCause());
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;

                        if (mRewardedAd != null) {
                            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdShowedFullScreenContent() {
                                    isAdShowing = true;
                                    if (AppOpenManager.getInstance().isInitialized()) {
                                        AppOpenManager.getInstance().isAppResumeEnabled = false;
                                    }
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    // Called when ad fails to show.
                                    if (adError.getCode() != 1) {
                                        AdmodUtils.getInstance().isAdShowing = false;
                                        adCallback2.onAdFail();
                                        mRewardedAd = null;
                                        dismissAdDialog();
                                    }
                                    if (AppOpenManager.getInstance().isInitialized()) {
                                        AppOpenManager.getInstance().isAppResumeEnabled = true;
                                    }
                                    Log.e("Admodfail", "onAdFailedToLoad" + adError.getMessage());
                                    Log.e("Admodfail", "errorCodeAds" + adError.getCause());

                                }

                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    // Called when ad is dismissed.
                                    // Set the ad reference to null so you don't show the ad a second time.
                                    mRewardedAd = null;
                                    AdmodUtils.getInstance().isAdShowing = false;
                                    adCallback2.onAdClosed();
                                    if (AppOpenManager.getInstance().isInitialized()) {
                                        AppOpenManager.getInstance().isAppResumeEnabled = true;
                                    }
                                }
                            });

                            if (ProcessLifecycleOwner.get().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
                                if (AppOpenManager.getInstance().isInitialized()) {
                                    AppOpenManager.getInstance().isAppResumeEnabled = false;

                                }
                                mRewardedAd.show(activity, new OnUserEarnedRewardListener() {
                                    @Override
                                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                        adCallback2.onEarned();
                                    }
                                });
                                isAdShowing = true;
                            } else {
                                mRewardedAd = null;
                                dismissAdDialog();
                                AdmodUtils.getInstance().isAdShowing = false;
                                if (AppOpenManager.getInstance().isInitialized()) {
                                    AppOpenManager.getInstance().isAppResumeEnabled = true;
                                }
                            }

                        } else {
                            AdmodUtils.getInstance().isAdShowing = false;
                            adCallback2.onAdFail();
                            dismissAdDialog();
                            if (AppOpenManager.getInstance().isInitialized()) {
                                AppOpenManager.getInstance().isAppResumeEnabled = true;
                            }
                        }
                    }
                });
    }

    //Interstitial Reward ads
    public RewardedInterstitialAd mInterstitialRewardAd;

    public void loadAdInterstitialReward(Context activity, String admobId, AdLoadCallback adLoadCallback) {
        if (!isShowAds || !isNetworkConnected(activity)) {
            return;
        }
        if (isTesting) {
            admobId = activity.getString(R.string.test_ads_admob_inter_reward_id);
        } else {
            if (admobId.equals(activity.getString(R.string.test_ads_admob_inter_reward_id)) && !BuildConfig.DEBUG) {

                Utils.getInstance().showDialogTitle(activity, "Warning", "Build bản release nhưng đang để id test ads", "Đã biết", DialogType.WARNING_TYPE, false, "", new DialogCallback() {
                    @Override
                    public void onClosed() {

                    }

                    @Override
                    public void cancel() {

                    }
                });
                return;
            }
        }

        RewardedInterstitialAd.load(activity, admobId, adRequest, new RewardedInterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull @org.jetbrains.annotations.NotNull RewardedInterstitialAd interstitialRewardAd) {
                mInterstitialRewardAd = interstitialRewardAd;
                adLoadCallback.onAdLoaded();
                Log.i("adLog", "onAdLoaded");
                // Toast.makeText(activity, "success load ads", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(@NonNull @org.jetbrains.annotations.NotNull LoadAdError loadAdError) {
                // Handle the error
                Log.i("adLog", loadAdError.getMessage());
                String error = String.format(
                        "domain: %s, code: %d, message: %s",
                        loadAdError.getDomain(), loadAdError.getCode(), loadAdError.getMessage());
//                Toast.makeText(
//                        activity, "onAdFailedToLoad() with error: " + error, Toast.LENGTH_SHORT)
//                        .show();
                adLoadCallback.onAdFail();
            }
        });
    }

    public void showAdInterstitialRewardWithCallback(RewardedInterstitialAd kInterstitialRewardAd, Activity activity, RewardAdCallback adCallback) {
        if (!isShowAds || !isNetworkConnected(activity)) {
            adCallback.onAdClosed();
            return;
        }
        if (kInterstitialRewardAd != null) {
            kInterstitialRewardAd.show(activity, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    adCallback.onEarned();
                }
            });
            kInterstitialRewardAd.setFullScreenContentCallback(
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            adCallback.onAdClosed();
                            isAdShowing = false;
                            Log.d("TAG", "The ad was dismissed.");
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            adCallback.onAdFail();
                            isAdShowing = false;
                            mInterstitialAd = null;
                            Log.d("TAG", "The ad failed to show.");
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            mInterstitialAd = null;
                            isAdShowing = true;
                            Log.d("TAG", "The ad was shown.");
                        }
                    });
        } else {
            // Toast.makeText(activity, "Ad did not load", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadAdInterstitial(Context activity, String admobId, AdCallbackNew adLoadCallback, boolean enableLoadingDialog) {

        AdmodUtils.getInstance().mInterstitialAd = null;
        AdmodUtils.getInstance().isAdShowing = false;

        if (!isShowAds || !isNetworkConnected(activity)) {
            adLoadCallback.onAdFail();
            return;
        }


        if (AppOpenManager.getInstance().isInitialized()) {
            if (!AppOpenManager.getInstance().isAppResumeEnabled) {
                return;
            } else {
                isAdShowing = false;
                if (AppOpenManager.getInstance().isInitialized()) {
                    AppOpenManager.getInstance().isAppResumeEnabled = false;
                }
            }
        }

        if (enableLoadingDialog) {
            dialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
            dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            dialog.setTitleText("Loading ads. Please wait...");
            dialog.setCancelable(false);
            dialog.show();
        }

        if (isTesting) {
            admobId = activity.getString(R.string.test_ads_admob_inter_id);
        }
        if (adRequest == null) {
            initAdRequest(timeOut);
        }
        idIntersitialReal = admobId;
        InterstitialAd.load(activity, idIntersitialReal, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull @org.jetbrains.annotations.NotNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
                adLoadCallback.onAdLoaded();
                Log.i("adLog", "onAdLoaded");
                // Toast.makeText(activity, "success load ads", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(@NonNull @org.jetbrains.annotations.NotNull LoadAdError loadAdError) {

                isAdShowing = false;
                if (AppOpenManager.getInstance().isInitialized()) {
                    AppOpenManager.getInstance().isAppResumeEnabled = true;
                }
                AdmodUtils.getInstance().isAdShowing = false;
                if (AdmodUtils.getInstance().mInterstitialAd != null) {
                    AdmodUtils.getInstance().mInterstitialAd = null;
                }
                adLoadCallback.onAdFail();

            }
        });
    }

    public void showAdInterstitialWithCallbackNotLoad(InterstitialAd kInterstitialAd, Activity activity, AdCallbackNew adCallback) {
        if (!isShowAds || !isNetworkConnected(activity)) {
            isAdShowing = false;
            if (AdmodUtils.getInstance().mInterstitialAd != null) {
                AdmodUtils.getInstance().mInterstitialAd = null;
            }
            if (AppOpenManager.getInstance().isInitialized()) {
                AppOpenManager.getInstance().isAppResumeEnabled = true;
            }
            adCallback.onAdClosed();
            return;

        }
        if (kInterstitialAd == null) {
            if (adCallback != null) {
                isAdShowing = false;
                if (AdmodUtils.getInstance().mInterstitialAd != null) {
                    AdmodUtils.getInstance().mInterstitialAd = null;
                }
                if (AppOpenManager.getInstance().isInitialized()) {
                    AppOpenManager.getInstance().isAppResumeEnabled = true;
                }
                adCallback.onAdFail();
            }
            return;
        }
        kInterstitialAd.setFullScreenContentCallback(
                new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        isAdShowing = false;
                        if (AdmodUtils.getInstance().mInterstitialAd != null) {
                            AdmodUtils.getInstance().mInterstitialAd = null;
                        }
                        if (AppOpenManager.getInstance().isInitialized()) {
                            AppOpenManager.getInstance().isAppResumeEnabled = true;
                        }
                        adCallback.onEventClickAdClosed();
                        adCallback.onAdClosed();

                        Log.d("TAG", "The ad was dismissed.");

                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        isAdShowing = false;
                        if (AppOpenManager.getInstance().isInitialized()) {
                            AppOpenManager.getInstance().isAppResumeEnabled = true;
                        }
                        AdmodUtils.getInstance().isAdShowing = false;
                        if (AdmodUtils.getInstance().mInterstitialAd != null) {
                            AdmodUtils.getInstance().mInterstitialAd = null;
                        }
                        Log.e("Admodfail", "onAdFailedToLoad" + adError.getMessage());
                        Log.e("Admodfail", "errorCodeAds" + adError.getCause());
                        adCallback.onAdFail();

                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        mInterstitialAd = null;
                        isAdShowing = true;
                        adCallback.onAdShowed();

                    }
                });
        showInterstitialAd(activity, kInterstitialAd, adCallback);
    }

    public void showAdInterstitialWithCallback(InterstitialAd kInterstitialAd, Activity activity, AdCallbackNew adCallback) {

        if (!isShowAds || !isNetworkConnected(activity)) {
            adCallback.onAdClosed();
            return;
        }
        if (kInterstitialAd == null) {
            if (adCallback != null) {
                adCallback.onAdFail();

            }
            return;
        }
        kInterstitialAd.setFullScreenContentCallback(
                new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        isAdShowing = false;
                        adCallback.onAdClosed();
                        adCallback.onEventClickAdClosed();
                        Log.d("TAG", "The ad was dismissed.");
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        adCallback.onAdFail();
                        isAdShowing = false;
                        mInterstitialAd = null;
                        loadAdInterstitial(activity, idIntersitialReal, new AdCallbackNew() {
                            @Override
                            public void onAdClosed() {

                            }

                            @Override
                            public void onEventClickAdClosed() {

                            }

                            @Override
                            public void onAdShowed() {

                            }

                            @Override
                            public void onAdFail() {
                                Log.d("TAG", "Ad loaded again fails");
                            }

                            @Override
                            public void onAdLoaded() {
                                Log.d("TAG", "Ad loaded again success");
                            }
                        }, false);
                        Log.d("TAG", "The ad failed to show.");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        mInterstitialAd = null;
                        isAdShowing = true;
                        adCallback.onAdShowed();

                        Log.d("TAG", "The ad was shown.");
                        loadAdInterstitial(activity, idIntersitialReal, new AdCallbackNew() {
                            @Override
                            public void onAdClosed() {

                            }

                            @Override
                            public void onEventClickAdClosed() {

                            }

                            @Override
                            public void onAdShowed() {

                            }

                            @Override
                            public void onAdFail() {
                                Log.d("TAG", "Ad loaded again fails");
                            }

                            @Override
                            public void onAdLoaded() {
                                Log.d("TAG", "Ad loaded again success");
                            }
                        }, false);
                    }
                });
        showInterstitialAd(activity, kInterstitialAd, adCallback);
    }

    private void showInterstitialAd(Activity activity, InterstitialAd mInterstitialAd, AdCallbackNew callback) {
        if (ProcessLifecycleOwner.get().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED) && mInterstitialAd != null) {
            AdmodUtils.getInstance().isAdShowing = true;
            if (callback != null) {
                callback.onAdClosed();
            }
            new Handler().postDelayed(() -> mInterstitialAd.show(activity), 400);
        } else {
            AdmodUtils.getInstance().isAdShowing = false;
            if (AppOpenManager.getInstance().isInitialized()) {
                AppOpenManager.getInstance().isAppResumeEnabled = true;
            }
            dismissAdDialog();
            Log.d("===Admod", "4");
            callback.onAdFail();
        }
    }

    public void loadAndShowAdInterstitialWithCallback(AppCompatActivity activity, String admobId, int limitTime, AdCallbackNew adCallback, boolean enableLoadingDialog) {
        AdmodUtils.getInstance().mInterstitialAd = null;
        AdmodUtils.getInstance().isAdShowing = false;

        if (adRequest == null) {
            initAdRequest(timeOut);
        }
        if (!isShowAds || !isNetworkConnected(activity)) {
            adCallback.onAdClosed();
//            handlerTimeOut.removeCallbacksAndMessages(null);
            return;
        }

        if (AppOpenManager.getInstance().isInitialized()) {
            if (!AppOpenManager.getInstance().isAppResumeEnabled) {
                return;
            } else {
                isAdShowing = false;
                if (AppOpenManager.getInstance().isInitialized()) {
                    AppOpenManager.getInstance().isAppResumeEnabled = false;
                }
            }
        }

        if (enableLoadingDialog) {
            dialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
            dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            dialog.setTitleText("Loading ads. Please wait...");
            dialog.setCancelable(false);
            dialog.show();
        }

        if (isTesting) {
            admobId = activity.getString(R.string.test_ads_admob_inter_id);
        } else {
            checkIdTest(activity, admobId);
        }

        InterstitialAd.load(activity, admobId, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull @org.jetbrains.annotations.NotNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                adCallback.onAdLoaded();

                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    mInterstitialAd = interstitialAd;
                    if (mInterstitialAd != null) {
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull @NotNull AdError adError) {
                                adCallback.onAdFail();
                                isAdShowing = false;
                                if (AppOpenManager.getInstance().isInitialized()) {
                                    AppOpenManager.getInstance().isAppResumeEnabled = true;
                                }
                                AdmodUtils.getInstance().isAdShowing = false;
                                if (AdmodUtils.getInstance().mInterstitialAd != null) {
                                    AdmodUtils.getInstance().mInterstitialAd = null;
                                }
                                Log.e("Admodfail", "onAdFailedToLoad" + adError.getMessage());
                                Log.e("Admodfail", "errorCodeAds" + adError.getCause());
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                lastTimeShowInterstitial = new Date().getTime();
                                adCallback.onAdClosed();
                                adCallback.onEventClickAdClosed();
                                if (AdmodUtils.getInstance().mInterstitialAd != null) {
                                    AdmodUtils.getInstance().mInterstitialAd = null;
                                }
                                isAdShowing = false;
                                if (AppOpenManager.getInstance().isInitialized()) {
                                    AppOpenManager.getInstance().isAppResumeEnabled = true;
                                }
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                adCallback.onAdShowed();
                                super.onAdShowedFullScreenContent();
                            }
                        });

                        if (activity.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED) && mInterstitialAd != null) {
                            mInterstitialAd.show(activity);
                            AdmodUtils.getInstance().isAdShowing = true;
                        } else {
                            mInterstitialAd = null;
                            dismissAdDialog();
                            AdmodUtils.getInstance().isAdShowing = false;
                            if (AppOpenManager.getInstance().isInitialized()) {
                                AppOpenManager.getInstance().isAppResumeEnabled = true;
                            }
                        }
                    } else {
                        dismissAdDialog();
                        adCallback.onAdFail();
                        AdmodUtils.getInstance().isAdShowing = false;
                        if (AppOpenManager.getInstance().isInitialized()) {
                            AppOpenManager.getInstance().isAppResumeEnabled = true;
                        }
                    }
                }, 800);

            }

            @Override
            public void onAdFailedToLoad(@NonNull @org.jetbrains.annotations.NotNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mInterstitialAd = null;
                adCallback.onAdFail();
                if (AppOpenManager.getInstance().isInitialized()) {
                    AppOpenManager.getInstance().isAppResumeEnabled = true;
                }
                AdmodUtils.getInstance().isAdShowing = false;
                dismissAdDialog();

            }
        });

    }

    public void loadAndShowAdInterstitialWithCallbackMultiAds(AppCompatActivity activity, String admobId, String admobId2, AdCallbackNew adCallback, boolean enableLoadingDialog) {
        AdmodUtils.getInstance().mInterstitialAd = null;
        AdmodUtils.getInstance().isAdShowing = false;
        if (adRequest == null) {
            initAdRequest(timeOut);
        }
        if (!isShowAds || !isNetworkConnected(activity)) {
            adCallback.onEventClickAdClosed();
//            handlerTimeOut.removeCallbacksAndMessages(null);
            return;
        }

        if (AppOpenManager.getInstance().isInitialized()) {
            if (!AppOpenManager.getInstance().isAppResumeEnabled) {

                return;
            } else {
                isAdShowing = false;
                if (AppOpenManager.getInstance().isInitialized()) {
                    AppOpenManager.getInstance().isAppResumeEnabled = false;
                }
            }
        }

        if (enableLoadingDialog) {
            dialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
            dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            dialog.setTitleText("Loading ads. Please wait...");
            dialog.setCancelable(false);
            dialog.show();
        }

        if (isTesting) {
            admobId = activity.getString(R.string.test_ads_admob_inter_id);
        } else {
            checkIdTest(activity, admobId);
        }

        String finalAdmobId2 = admobId2;

        InterstitialAd.load(activity, admobId, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull @org.jetbrains.annotations.NotNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                adCallback.onAdLoaded();

                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    mInterstitialAd = interstitialAd;
                    if (mInterstitialAd != null) {
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull @NotNull AdError adError) {
                                adCallback.onAdFail();
                                isAdShowing = false;
                                if (AppOpenManager.getInstance().isInitialized()) {
                                    AppOpenManager.getInstance().isAppResumeEnabled = true;
                                }
                                AdmodUtils.getInstance().isAdShowing = false;
                                if (AdmodUtils.getInstance().mInterstitialAd != null) {
                                    AdmodUtils.getInstance().mInterstitialAd = null;
                                }
                                Log.e("Admodfail", "onAdFailedToLoad" + adError.getMessage());
                                Log.e("Admodfail", "errorCodeAds" + adError.getCause());
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                lastTimeShowInterstitial = new Date().getTime();
                                adCallback.onAdClosed();
                                adCallback.onEventClickAdClosed();
                                if (AdmodUtils.getInstance().mInterstitialAd != null) {
                                    AdmodUtils.getInstance().mInterstitialAd = null;
                                }
                                isAdShowing = false;
                                if (AppOpenManager.getInstance().isInitialized()) {
                                    AppOpenManager.getInstance().isAppResumeEnabled = true;
                                }
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                adCallback.onAdShowed();
                                super.onAdShowedFullScreenContent();
                            }
                        });

                        if (activity.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED) && mInterstitialAd != null) {
                            mInterstitialAd.show(activity);
                            AdmodUtils.getInstance().isAdShowing = true;
                        } else {
                            mInterstitialAd = null;
                            dismissAdDialog();
                            AdmodUtils.getInstance().isAdShowing = false;
                            if (AppOpenManager.getInstance().isInitialized()) {
                                AppOpenManager.getInstance().isAppResumeEnabled = true;
                            }
                        }
                    } else {
                        dismissAdDialog();
                        adCallback.onAdFail();
                        AdmodUtils.getInstance().isAdShowing = false;
                        if (AppOpenManager.getInstance().isInitialized()) {
                            AppOpenManager.getInstance().isAppResumeEnabled = true;
                        }
                    }
                }, 800);

            }

            @Override
            public void onAdFailedToLoad(@NonNull @org.jetbrains.annotations.NotNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);

                mInterstitialAd = null;
                if (AppOpenManager.getInstance().isInitialized()) {
                    AppOpenManager.getInstance().isAppResumeEnabled = true;
                }
                loadAndShowAdInterstitialWithCallback2(activity, finalAdmobId2, adCallback);
            }
        });

    }


    private void loadAndShowAdInterstitialWithCallback2(AppCompatActivity activity, String admobId, AdCallbackNew adCallback) {
        AdmodUtils.getInstance().mInterstitialAd = null;
        AdmodUtils.getInstance().isAdShowing = false;


        if (adRequest == null) {
            initAdRequest(timeOut);
        }
        if (!isShowAds || !isNetworkConnected(activity)) {
            adCallback.onAdClosed();
//            handlerTimeOut.removeCallbacksAndMessages(null);
            return;
        }

        if (AppOpenManager.getInstance().isInitialized()) {
            if (!AppOpenManager.getInstance().isAppResumeEnabled) {

                return;
            } else {
                isAdShowing = false;
                if (AppOpenManager.getInstance().isInitialized()) {
                    AppOpenManager.getInstance().isAppResumeEnabled = false;
                }
            }
        }

        if (isTesting) {
            admobId = activity.getString(R.string.test_ads_admob_inter_id);
        } else {
            checkIdTest(activity, admobId);
        }

        InterstitialAd.load(activity, admobId, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull @org.jetbrains.annotations.NotNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                adCallback.onAdLoaded();
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    mInterstitialAd = interstitialAd;
                    if (mInterstitialAd != null) {
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull @NotNull AdError adError) {
                                adCallback.onAdFail();
                                isAdShowing = false;
                                if (AppOpenManager.getInstance().isInitialized()) {
                                    AppOpenManager.getInstance().isAppResumeEnabled = true;
                                }
                                AdmodUtils.getInstance().isAdShowing = false;
                                if (AdmodUtils.getInstance().mInterstitialAd != null) {
                                    AdmodUtils.getInstance().mInterstitialAd = null;
                                }
                                Log.e("Admodfail", "onAdFailedToLoad" + adError.getMessage());
                                Log.e("Admodfail", "errorCodeAds" + adError.getCause());
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                lastTimeShowInterstitial = new Date().getTime();
                                adCallback.onAdClosed();
                                adCallback.onEventClickAdClosed();
                                if (AdmodUtils.getInstance().mInterstitialAd != null) {
                                    AdmodUtils.getInstance().mInterstitialAd = null;
                                }
                                isAdShowing = false;
                                if (AppOpenManager.getInstance().isInitialized()) {
                                    AppOpenManager.getInstance().isAppResumeEnabled = true;
                                }
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                adCallback.onAdShowed();
                                super.onAdShowedFullScreenContent();
                            }
                        });

                        if (activity.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED) && mInterstitialAd != null) {
                            mInterstitialAd.show(activity);
                            AdmodUtils.getInstance().isAdShowing = true;
                        } else {
                            mInterstitialAd = null;
                            dismissAdDialog();
                            AdmodUtils.getInstance().isAdShowing = false;
                            if (AppOpenManager.getInstance().isInitialized()) {
                                AppOpenManager.getInstance().isAppResumeEnabled = true;
                            }
                        }
                    } else {
                        dismissAdDialog();
                        adCallback.onAdFail();
                        AdmodUtils.getInstance().isAdShowing = false;
                        if (AppOpenManager.getInstance().isInitialized()) {
                            AppOpenManager.getInstance().isAppResumeEnabled = true;
                        }
                    }
                }, 800);

            }

            @Override
            public void onAdFailedToLoad(@NonNull @org.jetbrains.annotations.NotNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);

                mInterstitialAd = null;
                if (AppOpenManager.getInstance().isInitialized()) {
                    AppOpenManager.getInstance().isAppResumeEnabled = true;
                }
                AdmodUtils.getInstance().isAdShowing = false;
                adCallback.onAdFail();
                dismissAdDialog();
            }
        });

    }

    public void loadAndGetAdInterstitial(Context activity, String admobId, String admobId2, AdCallBackInterLoad adLoadCallback) {
        AdmodUtils.getInstance().isAdShowing = false;

        if (!isShowAds || !isNetworkConnected(activity)) {
            adLoadCallback.onAdFail();
            return;
        }

        if (isTesting) {
            admobId = activity.getString(R.string.test_ads_admob_inter_id);
        }
        if (adRequest == null) {
            initAdRequest(timeOut);
        }
        idIntersitialReal = admobId;
        String idLoadInter2 = admobId2;
        InterstitialAd.load(activity, idIntersitialReal, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull @org.jetbrains.annotations.NotNull InterstitialAd interstitialAd) {
                adLoadCallback.onAdLoaded(interstitialAd);
                Log.i("adLog", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(@NonNull @org.jetbrains.annotations.NotNull LoadAdError loadAdError) {
                isAdShowing = false;
                AdmodUtils.getInstance().isAdShowing = false;
                if (AdmodUtils.getInstance().mInterstitialAd != null) {
                    AdmodUtils.getInstance().mInterstitialAd = null;
                }
                loadAndGetAdInterstitialId2(activity, idLoadInter2, adLoadCallback);
            }
        });
    }

    public void loadAndGetAdInterstitialId2(Context activity, String admobId2, AdCallBackInterLoad adLoadCallback) {
        AdmodUtils.getInstance().isAdShowing = false;

        if (!isShowAds || !isNetworkConnected(activity)) {
            adLoadCallback.onAdFail();
            return;
        }

        if (isTesting) {
            admobId2 = activity.getString(R.string.test_ads_admob_inter_id);
        }
        if (adRequest == null) {
            initAdRequest(timeOut);
        }
        idIntersitialReal = admobId2;
        InterstitialAd.load(activity, idIntersitialReal, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull @org.jetbrains.annotations.NotNull InterstitialAd interstitialAd) {
                adLoadCallback.onAdLoaded(interstitialAd);
                Log.i("adLog", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(@NonNull @org.jetbrains.annotations.NotNull LoadAdError loadAdError) {
                isAdShowing = false;
                AdmodUtils.getInstance().isAdShowing = false;
                if (AdmodUtils.getInstance().mInterstitialAd != null) {
                    AdmodUtils.getInstance().mInterstitialAd = null;
                }
                adLoadCallback.onAdFail();
            }
        });
    }

    public void showAdInterstitialWithCallbackNotLoadNew(InterstitialAd localInterstitialAd, Activity activity, AdCallbackNew adCallback, boolean enableLoadingDialog) {
        if (!isShowAds || !isNetworkConnected(activity)) {
            isAdShowing = false;
            if (AppOpenManager.getInstance().isInitialized()) {
                AppOpenManager.getInstance().isAppResumeEnabled = true;
            }
            Log.d("===Admod", "1");
            adCallback.onAdFail();
            return;
        }
        if (localInterstitialAd == null) {
            if (adCallback != null) {
                isAdShowing = false;
                if (AppOpenManager.getInstance().isInitialized()) {
                    AppOpenManager.getInstance().isAppResumeEnabled = true;
                }
                Log.d("===Admod", "2");
                adCallback.onAdFail();
            }
            return;
        }

        if (enableLoadingDialog) {
            dialogLoading(activity);
        }
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            localInterstitialAd.setFullScreenContentCallback(
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            isAdShowing = false;
                            if (AppOpenManager.getInstance().isInitialized()) {
                                AppOpenManager.getInstance().isAppResumeEnabled = true;
                            }
                            adCallback.onEventClickAdClosed();
                            dismissAdDialog();
                            Log.d("TAG", "The ad was dismissed.");
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            isAdShowing = false;
                            if (AppOpenManager.getInstance().isInitialized()) {
                                AppOpenManager.getInstance().isAppResumeEnabled = true;
                            }
                            AdmodUtils.getInstance().isAdShowing = false;
                            dismissAdDialog();
                            Log.e("Admodfail", "onAdFailedToLoad" + adError.getMessage());
                            Log.e("Admodfail", "errorCodeAds" + adError.getCause());
                            Log.d("===Admod", "3");
                            adCallback.onAdFail();
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            isAdShowing = true;
                            adCallback.onAdShowed();
                        }
                    });
            showInterstitialAd(activity, localInterstitialAd, adCallback);
        }, 400);
    }

    public void loadGetAndShowInterstitial(Context activity, String admobId, String admobId2, AdCallBackInterLoad adLoadCallback, boolean enableLoadingDialog) {

        AdmodUtils.getInstance().mInterstitialAd = null;
        AdmodUtils.getInstance().isAdShowing = false;

        if (!isShowAds || !isNetworkConnected(activity)) {
            adLoadCallback.onAdFail();
            return;
        }

        if (AppOpenManager.getInstance().isInitialized()) {
            if (!AppOpenManager.getInstance().isAppResumeEnabled) {
                return;
            } else {
                isAdShowing = false;
                if (AppOpenManager.getInstance().isInitialized()) {
                    AppOpenManager.getInstance().isAppResumeEnabled = false;
                }
            }
        }

        if (enableLoadingDialog) {
            dialogLoading(activity);
        }

        if (isTesting) {
            admobId = activity.getString(R.string.test_ads_admob_inter_id);
        }
        if (adRequest == null) {
            initAdRequest(timeOut);
        }
        idIntersitialReal = admobId;
        String id2 = admobId2;
        InterstitialAd.load(activity, idIntersitialReal, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull @org.jetbrains.annotations.NotNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
                adLoadCallback.onAdLoaded(interstitialAd);
                Log.i("adLog", "onAdLoaded");
                // Toast.makeText(activity, "success load ads", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(@NonNull @org.jetbrains.annotations.NotNull LoadAdError loadAdError) {
                isAdShowing = false;
                if (AppOpenManager.getInstance().isInitialized()) {
                    AppOpenManager.getInstance().isAppResumeEnabled = true;
                }
                AdmodUtils.getInstance().isAdShowing = false;
                if (AdmodUtils.getInstance().mInterstitialAd != null) {
                    AdmodUtils.getInstance().mInterstitialAd = null;
                }
                loadGetAndShowInterstitialId2(activity, id2, adLoadCallback, false);
            }
        });
    }

    public void loadGetAndShowInterstitialId2(Context activity, String admobId, AdCallBackInterLoad adLoadCallback, boolean enableLoadingDialog) {

        AdmodUtils.getInstance().mInterstitialAd = null;
        AdmodUtils.getInstance().isAdShowing = false;

        if (!isShowAds || !isNetworkConnected(activity)) {
            adLoadCallback.onAdFail();
            return;
        }

        if (AppOpenManager.getInstance().isInitialized()) {
            if (!AppOpenManager.getInstance().isAppResumeEnabled) {
                return;
            } else {
                isAdShowing = false;
                if (AppOpenManager.getInstance().isInitialized()) {
                    AppOpenManager.getInstance().isAppResumeEnabled = false;
                }
            }
        }

        if (enableLoadingDialog) {
            dialogLoading(activity);
        }

        if (isTesting) {
            admobId = activity.getString(R.string.test_ads_admob_inter_id);
        }
        if (adRequest == null) {
            initAdRequest(timeOut);
        }
        idIntersitialReal = admobId;
        InterstitialAd.load(activity, idIntersitialReal, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull @org.jetbrains.annotations.NotNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
                adLoadCallback.onAdLoaded(interstitialAd);
                Log.i("adLog", "onAdLoaded");
                // Toast.makeText(activity, "success load ads", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(@NonNull @org.jetbrains.annotations.NotNull LoadAdError loadAdError) {

                isAdShowing = false;
                if (AppOpenManager.getInstance().isInitialized()) {
                    AppOpenManager.getInstance().isAppResumeEnabled = true;
                }
                AdmodUtils.getInstance().isAdShowing = false;
                if (AdmodUtils.getInstance().mInterstitialAd != null) {
                    AdmodUtils.getInstance().mInterstitialAd = null;
                }
                adLoadCallback.onAdFail();

            }
        });
    }

    public void showAdInterstitial(InterstitialAd localInterstitialAd, Activity activity, AdCallbackNew adCallback, boolean enableLoadingDialog) {
        if (!isShowAds || !isNetworkConnected(activity)) {
            isAdShowing = false;
            if (AppOpenManager.getInstance().isInitialized()) {
                AppOpenManager.getInstance().isAppResumeEnabled = true;
            }
            Log.d("===Admod", "1");
            adCallback.onAdFail();
            return;
        }
        if (localInterstitialAd == null) {
            if (adCallback != null) {
                isAdShowing = false;
                if (AppOpenManager.getInstance().isInitialized()) {
                    AppOpenManager.getInstance().isAppResumeEnabled = true;
                }
                Log.d("===Admod", "2");
                adCallback.onAdFail();
            }
            return;
        }

        if (enableLoadingDialog) {
            dialogLoading(activity);
        }
        localInterstitialAd.setFullScreenContentCallback(
                new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        isAdShowing = false;
                        if (AppOpenManager.getInstance().isInitialized()) {
                            AppOpenManager.getInstance().isAppResumeEnabled = true;
                        }
                        adCallback.onEventClickAdClosed();
                        dismissAdDialog();
                        Log.d("TAG", "The ad was dismissed.");
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        isAdShowing = false;
                        if (AppOpenManager.getInstance().isInitialized()) {
                            AppOpenManager.getInstance().isAppResumeEnabled = true;
                        }
                        AdmodUtils.getInstance().isAdShowing = false;
                        dismissAdDialog();
                        Log.e("Admodfail", "onAdFailedToLoad" + adError.getMessage());
                        Log.e("Admodfail", "errorCodeAds" + adError.getCause());
                        Log.d("===Admod", "3");
                        adCallback.onAdFail();
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        isAdShowing = true;
                        adCallback.onAdShowed();
                    }
                });
        if (ProcessLifecycleOwner.get().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED) && mInterstitialAd != null) {
            AdmodUtils.getInstance().isAdShowing = true;
            if (adCallback != null) {
                adCallback.onAdClosed();
            }
            new Handler().postDelayed(() -> mInterstitialAd.show(activity), 100);
        } else {
            AdmodUtils.getInstance().isAdShowing = false;
            if (AppOpenManager.getInstance().isInitialized()) {
                AppOpenManager.getInstance().isAppResumeEnabled = true;
            }
            dismissAdDialog();
            Log.d("===Admod", "4");
            adCallback.onAdFail();
        }
    }

    public void dismissAdDialog() {
        if (AdmodUtils.getInstance().dialog != null && AdmodUtils.getInstance().dialog.isShowing()) {
            AdmodUtils.getInstance().dialog.dismiss();
        }
        if (AdmodUtils.getInstance().dialogFullScreen != null && AdmodUtils.getInstance().dialogFullScreen.isShowing()) {
            AdmodUtils.getInstance().dialogFullScreen.dismiss();
        }
    }

    private void checkIdTest(Activity activity, String admobId) {
//        if (admobId.equals(activity.getString(R.string.test_ads_admob_inter_id)) && !BuildConfig.DEBUG) {
//            if (dialog != null) {
//                dialog.dismiss();
//            }
//            Utils.getInstance().showDialogTitle(activity, "Warning", "Build bản release nhưng đang để id test ads", "Đã biết", DialogType.WARNING_TYPE, false, "", new DialogCallback() {
//                @Override
//                public void onClosed() {
//                }
//
//                @Override
//                public void cancel() {
//                }
//            });
//        }
    }

    private long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public String getDeviceID(Context context) {
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceId = md5(android_id).toUpperCase();
        return deviceId;
    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void dialogLoading(Context context){
        dialogFullScreen = new Dialog(context);
        dialogFullScreen.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFullScreen.setContentView(R.layout.dialog_full_screen);
        dialogFullScreen.setCancelable(false);
        dialogFullScreen.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialogFullScreen.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        dialogFullScreen.show();
    }
}
