# Admob Utils Library

[![](https://jitpack.io/v/dktlib/AdmobUtilsLibrary.svg)](https://jitpack.io/#dktlib/AdmobUtilsLibrary)
- Adding the library to your project:
Add the following in your root build.gradle at the end of repositories:
```bash
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }	    
    }
}
```
Implement library in your app level build.gradle:
```bash
dependencies {
    implementation 'com.github.dktlib:AdmobUtilsLibrary:{version}'
}
```


#  init Aplication
```bash
public class MyApplication extends Application {
    boolean isShowAds = true;
    boolean isShowAdsResume = true;

    @Override
    public void onCreate() {
        super.onCreate();
        AdmodUtils.initAdmob(this, 10000, true, isShowAds);
        if (isShowAdsResume) {
            AppOpenManager.getInstance().init(this, getString(R.string.ads_admob_app_open));
            AppOpenManager.getInstance().disableAppResumeWithActivity(SplashActivity.class);
        }
    }
}
```
- Add MyApplication to manifest
```bash 
<application
   android:name=".MyApplication"
 />
```
#  AOA (App Open Ads)
 ```bash 
       //AOA thường dùng để load QC ở màn Splash
       String idAOA = "ca-app-pub-3940256099942544/3419835294";
       AOAManager aoaManager = new AOAManager(this, idAOA, 10000, new AOAManager.AppOpenAdsListener() {
            @Override
            public void onAdsClose() {
                Utils.getInstance().replaceActivity(SplashActivity.this, MainActivity.class);
            }

            @Override
            public void onAdsFailed() {
                Utils.getInstance().replaceActivity(SplashActivity.this, MainActivity.class);
            }
        });
        aoaManager.loadAndShowAoA();
 ```
#  Interstitial
- loadAdInterstitial
 ```bash 
  // Hàm loadAdInterstitial sẽ được gọi trước khi show một màn VD: Muốn show Interstitial ở màn IntroActivity thì sẽ gọi trước hàm load từ màn SplashActivity, đến IntroActivity chỉ cần show.
    var interholder = InterHolder(
        "ca-app-pub-3940256099942544/1033173712",
        "ca-app-pub-3940256099942544/1033173712"
    )
    //mỗi vị trí Interstitial sẽ có 2 id qc
       
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
    
 ```
-  showAdInterstitialWithCallback
  ```bash 
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
                    loadInter(context, interHolder)
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
                    loadInter(context, interHolder)
                    callback.onAdClosedOrFailed()
                }

                override fun onPaid(adValue: AdValue?) {

                }
            },
            true
        )
 ```
#  AdReward
```bash 
   AdmodUtils.loadAndShowAdRewardWithCallback(MainActivity.this, getString(R.string.test_ads_admob_reward_id), new RewardAdCallback() {
                    @Override
                    public void onAdClosed() {
                        if (AdmodUtils.mRewardedAd != null) {
                            AdmodUtils.mRewardedAd = null;
                        }
                        AdmodUtils.dismissAdDialog();
                        //Utils.getInstance().showMessenger(MainActivity.this, "close ad");
                        startActivity(new Intent(MainActivity.this, OtherActivity.class));
                    }

                    @Override
                    public void onEarned() {
                        if (AdmodUtils.mRewardedAd != null) {
                            AdmodUtils.mRewardedAd = null;
                        }
                        AdmodUtils.dismissAdDialog();
                        Utils.getInstance().showMessenger(MainActivity.this, "Reward");

                    }

                    @Override
                    public void onAdFail() {
                        //code here
                        Utils.getInstance().showMessenger(MainActivity.this, "Reward fail");
                    }
                }, true);
           
```
#  AdBanner
- Load và show Banner thường
```bash
           var adId = "ca-app-pub-3940256099942544/6300978111"
           AdmodUtils.loadAdBanner(activity, adId, viewGroup, object :
                AdmodUtils.BannerCallBack {
                override fun onLoad() {
                    viewGroup.visibility = View.VISIBLE
                    line.visibility = View.VISIBLE
                }

                override fun onFailed() {
                    viewGroup.visibility = View.GONE
                    line.visibility = View.GONE
                }

                override fun onPaid(adValue: AdValue?, mAdView: AdView?) {
                }
            })
            // viewGroup là FrameLayout và để minHeight="50dp"
```
- Load và show Banner Collapsible
```bash
            var adId = "ca-app-pub-3940256099942544/6300978111"
            AdmodUtils.loadAdBannerCollapsible(
                activity,
                adId,
                CollapsibleBanner.BOTTOM,
                viewGroup,
                object : BannerAdCallback {
                    override fun onBannerAdLoaded(adSize: AdSize) {
                        viewGroup.visibility = View.VISIBLE
                        val params: ViewGroup.LayoutParams = viewGroup.layoutParams
                        params.height = adSize.getHeightInPixels(activity)
                        viewGroup.layoutParams = params
                    }

                    override fun onAdFail() {
                        viewGroup.visibility = View.GONE
                    }

                    override fun onAdPaid(adValue: AdValue?) {
                    }
                })
        } else {
            view.visibility = View.GONE
            line.visibility = View.GONE
        }
        // viewGroup là FrameLayout và để minHeight="50dp"
```

#  AdNative
- Load AdNative
```bash
 // Hàm Load AdNative sẽ được gọi trước khi show một màn VD: Muốn show Interstitial ở màn IntroActivity thì sẽ gọi trước hàm Load từ màn SplashActivity, đến IntroActivity chỉ cần show.
    var nativeHolder = NativeHolder(
        "ca-app-pub-3940256099942544/2247696110",
        "ca-app-pub-3940256099942544/2247696110"
    )
    //mỗi vị trí native sẽ có 2 id qc
    
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
```

- Show AdNative
```bash 
    AdmodUtils.showNativeAdsWithLayout(activity, nativeHolder, nativeAdContainer, R.layout.ad_template_medium, GoogleENative.UNIFIED_MEDIUM, object : AdmodUtils.AdsNativeCallBackAdmod {
            override fun NativeLoaded() {
                Log.d("===NativeAds", "Native showed")
                nativeAdContainer.visibility = View.VISIBLE
            }

            override fun NativeFailed() {
                Log.d("===NativeAds", "Native false")
                nativeAdContainer.visibility = View.GONE
            }
        })
        // ad_template_medium, ad_template_small là file xml nằm trong project example
        // GoogleENative.UNIFIED_MEDIUM, GoogleENative.UNIFIED_SMALL dùng để chỉnh kích thước màn loading trước khi show ad
         // nativeAdContainer là FrameLayout và để minHeight="200dp"
```

# PurchaseUtils
- init
```bash
PurchaseUtils.getInstance().initBilling(Context context,String play_console_license);
```
- subscribeById
```bash
PurchaseUtils.getInstance().subscribeById(Activity context, String idSubscribe);
```
- getDetailSubscribe
```bash
PurchaseUtils.getInstance().getDetailSubscribe(Activity context, String idSubscribe);
```
- isSubscription
```bash
PurchaseUtils.getInstance().isSubscriptiond(String idSubscribe);
```
- purchaseById
```bash
PurchaseUtils.getInstance().purchaseById(Activity context, String idPurchased);
```
- getDetailPurchased
```bash
PurchaseUtils.getInstance().getDetailPurchase(Activity context, String idPurchased);
```
- isPurchased
```bash
PurchaseUtils.getInstance().isPurchased(String idPurchased);
```
# Utils
```bash
Utils.getInstance().showMessenger(Context context, String content)
 ```
 ```bash
 Utils.getInstance().addActivity(Context context, Class activity)
 ```
 ```bash
 Utils.getInstance()replaceActivity(Context context, Class activity)
 ```
 ```bash
 Utils.getInstance()addFragment(AppCompatActivity context, Fragment fragment, int contentFrame, boolean addToBackStack)
 ```
  ```bash
 Utils.getInstance()replaceFragment(FragmentManager fm, Fragment fragment, int contentFrame, boolean addToBackStack)
 ```
