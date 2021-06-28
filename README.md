# Admob Utils Library

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

        AdmodUtils.getInstance().initAdmob(this, true, true, isShowAds);

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
                    }

                    @Override
                    public void onAdFail() {
                      //code here
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
//                        Utils.getInstance().replaceActivity(MainActivity.this,OtherActivity.class);
                        Utils.getInstance().addActivity(MainActivity.this,OtherActivity.class);

                    }

                    @Override
                    public void onAdFail() {
                        //code here
//                        Utils.getInstance().replaceActivity(MainActivity.this,OtherActivity.class);
                        Utils.getInstance().addActivity(MainActivity.this,OtherActivity.class);
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
# PurchaseUtils
- init
```bash
PurchaseUtils.getInstance().initBilling(Context context,String play_console_license);
```
- subscribe
```bash
PurchaseUtils.getInstance().subscribe(Activity context, String idSubscribe);
```
- isPurchased
```bash
PurchaseUtils.getInstance().isPurchased(String idSubscribe);
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
