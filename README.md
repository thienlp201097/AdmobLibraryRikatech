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
    implementation 'com.github.Namdh1212:AdmobUtilsLibrary:{version}'
    //multidex
    implementation "androidx.multidex:multidex:2.0.1"
}
```
```bash
defaultConfig {
 multiDexEnabled true
  }
```

- init Aplication
```bash
public class MyApplication extends AdsMultiDexApplication {
    boolean isShowAds = true;
    boolean isShowAdsResume = true;

    @Override
    public void onCreate() {
        super.onCreate();

        PurchaseUtils.getInstance().initBilling(this,getString(R.string.play_console_license));
        if (PurchaseUtils.getInstance().isPurchased(getString(R.string.premium))) {
            isShowAds = false;
        }else {
            isShowAds = true;
        }

        AdmodUtils.getInstance().initAdmob(this, BuildConfig.DEBUG, BuildConfig.DEBUG, isShowAds);

        if (isShowAdsResume) {
            AppOpenManager.getInstance().init(this, getString(R.string.ads_admob_app_open));
            AppOpenManager.getInstance().disableAppResumeWithActivity(SplashActivity.class);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
```
- Add MyApplication to manifest
```bash 
<application
   android:name=".MyApplication"
 />
```
- Interstitial
  + loadAndShowAdInterstitialWithCallback

```bash 
AdmodUtils.getInstance().loadAndShowAdInterstitialWithCallback(context, admobId, limitTime, 
      new AdCallback() {
                    @Override
                    public void onAdClosed() {
                      //code here
                      // Utils.getInstance().replaceActivity(MainActivity.this,OtherActivity.class);
                      // Utils.getInstance().addActivity(MainActivity.this,OtherActivity.class);
                    }

                    @Override
                    public void onAdFail() {
                      //code here
                      // Utils.getInstance().replaceActivity(MainActivity.this,OtherActivity.class);
                      // Utils.getInstance().addActivity(MainActivity.this,OtherActivity.class);
                    }
                }, isEnableDialog);

// admobId:String
// limitTime:Int (milisecond)
// isEnableDialog:Bool 
```
  + loadAdInterstitial
 ```bash 
                AdmodUtils.getInstance().loadAndShowAdInterstitialWithCallback(MainActivity.this, getString(R.string.ads_admob_inter_id), 0, new AdCallback() {
                    @Override
                    public void onAdClosed() {
                      //code here
                      // Utils.getInstance().replaceActivity(MainActivity.this,OtherActivity.class);
                      // Utils.getInstance().addActivity(MainActivity.this,OtherActivity.class);

                    }

                    @Override
                    public void onAdFail() {
                      //code here
                      // Utils.getInstance().replaceActivity(MainActivity.this,OtherActivity.class);
                      // Utils.getInstance().addActivity(MainActivity.this,OtherActivity.class);
                    }
                }, true);
 ```
  + showAdInterstitialWithCallback
  ```bash 
         AdmodUtils.getInstance().loadAndShowAdRewardWithCallback(MainActivity.this, getString(R.string.ads_admob_reward_id), new AdCallback() {
                    @Override
                    public void onAdClosed() {
                        //code here
                        Utils.getInstance().showMessenger(MainActivity.this,"Reward");

                    }

                    @Override
                    public void onAdFail() {
                        //code here
                        Utils.getInstance().showMessenger(MainActivity.this,"Reward fail");
                    }
                }, true);
 ```
- AdReward
```bash 
 AdmodUtils.getInstance().loadAndShowAdRewardWithCallback(MainActivity.this, getString(R.string.ads_admob_reward_id), new AdCallback() {
                    @Override
                    public void onAdClosed() {
                        //code here
                        Utils.getInstance().showMessenger(MainActivity.this,"Reward");

                    }

                    @Override
                    public void onAdFail() {
                        //code here
                        Utils.getInstance().showMessenger(MainActivity.this,"Reward fail");
                    }
                }, true);
```
- AdBanner
```bash 
AdmodUtils.getInstance().loadAdBanner(MainActivity.this, getString(R.string.ads_admob_native_id), viewGroup_banner);
```
- AdNative
```bash 
AdmodUtils.getInstance().loadNativeAds(MainActivity.this, getString(R.string.ads_admob_native_id), viewGroup_nativeAds, GoogleENative.UNIFIED_SMALL);
//GoogleENative = UNIFIED_MEDIUM | UNIFIED_SMALL
```
- AdNative in recyclerview
```bash 
 recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MainAdapter mainAdapter = new MainAdapter(itemModel, this::onNavigate);
        GoogleNativeAdAdapter googleNativeAdAdapter = new GoogleNativeAdAdapter(
                new GoogleNativeAdAdapter.Param(
                        NativeRecyclerActivity.this,
                        mainAdapter, GoogleENative.UNIFIED_SMALL,
                        2,
                        R.layout.layout_ad, R.id.layout_ad));
        recyclerView.setAdapter(googleNativeAdAdapter);
        //GoogleENative = UNIFIED_MEDIUM | UNIFIED_SMALL
        //2 is position show ad native
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
