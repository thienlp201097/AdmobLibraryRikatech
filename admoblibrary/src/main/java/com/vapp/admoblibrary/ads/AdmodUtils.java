package com.vapp.admoblibrary.ads;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.ads.google.admobads.admobnative.GoogleENative;
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
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.vapp.admoblibrary.R;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class AdmodUtils {
    ProgressDialog dialog;
    public long lastTimeShowInterstitial = 0;
    public boolean isAdShowing = false;
    public boolean isShowAds = true;
    public boolean isTesting = false;
    List<String> testDevices = new ArrayList<>();
    private static volatile AdmodUtils INSTANCE;

    public static synchronized AdmodUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AdmodUtils();
        }
        return INSTANCE;
    }


    public void initAdmob(Context context, boolean isDebug, boolean isAddDeviceTest, boolean isEnableAds) {
        if (!isEnableAds) {
            isShowAds = false;
        }
        MobileAds.initialize(context, initializationStatus -> {
        });

        initListIdTest();
        if (isAddDeviceTest) {
            testDevices.add(getDeviceID(context));
        }
        RequestConfiguration requestConfiguration
                = new RequestConfiguration.Builder()
                .setTestDeviceIds(testDevices)
                .build();
        MobileAds.setRequestConfiguration(requestConfiguration);
        initAdRequest();

        if (isDebug) {
            isTesting = true;
        } else {
            isTesting = false;
        }
    }

    AdRequest adRequest;

    // get AdRequest
    public void initAdRequest() {
        adRequest = new AdRequest.Builder()
                .setHttpTimeoutMillis(5000)
                .build();
    }

    void initListIdTest() {
        testDevices.add("3C94990AA9A387A256D3B2BBBFEA51EA");
        testDevices.add("6F599887BC401CFB1C7087F15D7C0834");
        testDevices.add("B543DCF2C7591C7FB8B52A3A1E7138F6");
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
    }


    //check open network
    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


    public void loadAdBanner(Context context,  String bannerId, ViewGroup viewGroup) {

        if (!isShowAds){
            viewGroup.setVisibility(View.GONE);
            return;
        }

        AdView mAdView = new AdView(context);
        if (isTesting) {
            bannerId = context.getString(R.string.ads_admob_banner_id);
        }
        mAdView.setAdUnitId(bannerId);
        mAdView.setAdSize(AdSize.SMART_BANNER);
        viewGroup.removeAllViews();
        viewGroup.addView(mAdView);
        mAdView.loadAd(adRequest);
        Log.e(" Admod", "loadAdBanner");
    }

    // ads native
    @SuppressLint("StaticFieldLeak")
    public void loadNativeAds(Activity activity, String s, ViewGroup viewGroup, GoogleENative size) {

        if (!isShowAds){
            viewGroup.setVisibility(View.GONE);
            return;
        }

        AdLoader adLoader;
        if (isTesting) {
            s = activity.getString(R.string.ads_admob_native_id);
        }
        adLoader = new AdLoader.Builder(activity, s)
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                        int id = 0;
                        if (size == GoogleENative.UNIFIED_MEDIUM) {
                            id = R.layout.ad_unified_medium;
                        } else {
                            id = R.layout.ad_unified_small;
                        }

                        NativeAdView adView = (NativeAdView) activity.getLayoutInflater()
                                .inflate(id, null);

                        viewGroup.setVisibility(View.VISIBLE);
                        viewGroup.removeAllViews();
                        viewGroup.addView(adView);

                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {

                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()

                        .build())
                .build();

        adLoader.loadAd(adRequest);
        Log.e(" Admod", "loadAdNativeAds");
    }

    //reward
    RewardedAd mRewardedAd = null;

    public void loadAndShowAdRewardWithCallback(Activity activity, String admobId, AdCallback adCallback2, boolean enableLoadingDialog) {
        if (!isShowAds){
            adCallback2.onAdClosed();
            return;
        }

        if (isTesting) {
            admobId = activity.getString(R.string.ads_admob_reward_id);
        }
        if (enableLoadingDialog) {
            dialog = new ProgressDialog(activity, R.style.AppCompatAlertDialogStyle);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Loading");
            dialog.setMessage("Loading ads. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        RewardedAd.load(activity, admobId,
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        mRewardedAd = null;
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        adCallback2.onAdFail();
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        if (mRewardedAd != null) {
                            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdShowedFullScreenContent() {
                                    // Called when ad is shown.
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    // Called when ad fails to show.
                                    if (dialog != null) {
                                        dialog.dismiss();
                                    }
                                    adCallback2.onAdFail();
                                    mRewardedAd = null;

                                }

                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    // Called when ad is dismissed.
                                    // Set the ad reference to null so you don't show the ad a second time.
                                    mRewardedAd = null;
                                    if (dialog != null) {
                                        dialog.dismiss();
                                    }
                                }
                            });

                            mRewardedAd.show(activity, new OnUserEarnedRewardListener() {
                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                    // Handle the reward.
                                    adCallback2.onAdClosed();
                                    if (AppOpenManager.getInstance().isInitialized()) {
                                        AppOpenManager.getInstance().enableAppResume();
                                    }
                                }


                            });
                        } else {
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                            adCallback2.onAdFail();
                        }
                    }
                });


    }

    //inter ads
    public InterstitialAd mInterstitialAd;

    public void loadAdInterstitial(Context context, String s, boolean enableLoadingDialog) {

        if (isTesting) {
            s = context.getString(R.string.ads_admob_inter_id);
        }

        if (enableLoadingDialog) {
            dialog = new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Loading");
            dialog.setMessage("Loading ads. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        InterstitialAd.load(context, s, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull @org.jetbrains.annotations.NotNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                mInterstitialAd = interstitialAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull @org.jetbrains.annotations.NotNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mInterstitialAd = null;

            }
        });


    }

    public void showAdInterstitialWithCallback(Activity activity, AdCallback adCallback, int limitTime) {

        if (!isShowAds){
            adCallback.onAdClosed();
            return;
        }

        long currentTime = getCurrentTime();
        if (mInterstitialAd != null) {
            if (currentTime - lastTimeShowInterstitial >= limitTime) {

                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull @NotNull AdError adError) {
                        Log.e(" Admod", "showAdInterstitial");
                        Log.e(" Admod", "errorCodeAds:" + adError.getMessage());
                        Log.e(" Admod", "errorCodeAds:" + adError.getCause());
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        adCallback.onAdClosed();
                        if (AppOpenManager.getInstance().isInitialized()) {
                            AppOpenManager.getInstance().enableAppResume();
                        }
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        adCallback.onAdClosed();

                        if (AppOpenManager.getInstance().isInitialized()) {
                            AppOpenManager.getInstance().enableAppResume();
                        }
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                    }
                });
            } else {
                adCallback.onAdClosed();
                if (AppOpenManager.getInstance().isInitialized()) {
                    AppOpenManager.getInstance().enableAppResume();
                }
            }
        }
        if (mInterstitialAd != null) {
            if (ProcessLifecycleOwner.get().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
                try {
                    if (dialog != null && dialog.isShowing())
                        dialog.dismiss();
                } catch (Exception e) {
                    dialog = null;
                    e.printStackTrace();
                }
                new Handler().postDelayed(() -> {
                    if (AppOpenManager.getInstance().isInitialized()) {
                        AppOpenManager.getInstance().disableAppResume();
                    }
                    mInterstitialAd.show(activity);
                }, 800);

            }
        }
    }

    public void loadAndShowAdInterstitialWithCallback(Activity activity, String admobId, int limitTime, AdCallback adCallback, boolean enableLoadingDialog) {
        if (!isShowAds){
            adCallback.onAdClosed();
            return;
        }

        long currentTime = getCurrentTime();
        if (currentTime - lastTimeShowInterstitial >= limitTime) {
            if (isTesting) {
                admobId = activity.getString(R.string.ads_admob_inter_id);
            }
            if (enableLoadingDialog) {
                dialog = new ProgressDialog(activity, R.style.AppCompatAlertDialogStyle);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setTitle("Loading");
                dialog.setMessage("Loading ads. Please wait...");
                dialog.setIndeterminate(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }

            InterstitialAd.load(activity, admobId, adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull @org.jetbrains.annotations.NotNull InterstitialAd interstitialAd) {
                    super.onAdLoaded(interstitialAd);
                    mInterstitialAd = interstitialAd;

                    if (mInterstitialAd != null) {
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull @NotNull AdError adError) {
                                adCallback.onAdClosed();
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                                isAdShowing = false;
                                if (AppOpenManager.getInstance().isInitialized()) {
                                    AppOpenManager.getInstance().enableAppResume();
                                }
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                adCallback.onAdClosed();
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                                isAdShowing = false;
                                if (AppOpenManager.getInstance().isInitialized()) {
                                    AppOpenManager.getInstance().enableAppResume();
                                }
                            }
                        });

                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        if (ProcessLifecycleOwner.get().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
                            try {
                                if (dialog != null && dialog.isShowing())
                                    dialog.dismiss();
                            } catch (Exception e) {
                                dialog = null;
                                e.printStackTrace();
                            }
                            new Handler().postDelayed(() -> {
                                if (AppOpenManager.getInstance().isInitialized()) {
                                    AppOpenManager.getInstance().disableAppResume();
                                }

                                mInterstitialAd.show(activity);
                            }, 800);

                        }

                    } else {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        adCallback.onAdFail();
                    }

                }

                @Override
                public void onAdFailedToLoad(@NonNull @org.jetbrains.annotations.NotNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    mInterstitialAd = null;
                    adCallback.onAdFail();
                }
            });


        } else {
            adCallback.onAdClosed();
        }


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
}
