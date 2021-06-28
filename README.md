# AdmobUtilsLibrary

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
    implementation 'com.'
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

    @Override
    public void onCreate() {
        super.onCreate();
        
        AdmodUtils.initAdmob(this, isDebug, isAddDeviceTest, isEnableAds);        
        // isDebug:bool = use admob id test
        // isAddDeviceTest:bool = get and add device test this device
        // isEnableAds:bool = is show ads
        
        if (enableAdsResume()) {
            AppOpenManager.getInstance().init(this, getOpenAppAdId());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public boolean enableAdsResume() {
        return true;
    }

    @Override
    public String getOpenAppAdId() {
        return "ca-app-pub-3940256099942544/3419835294";
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
AdmodUtils.loadAndShowAdInterstitialWithCallback(context, admobId, limitTime, 
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
  + showAdInterstitialWithCallback
- AdBanner
- AdReward
- AdNative
- AppOpenAds
# PurchaseUtils
- init
```bash
PurchaseUtils.initBilling(Context context,String play_console_license, String idSubscribe);
```
- subscribe
```bash
PurchaseUtils.subscribe(Activity context, String idSubscribe);
```
- isPurchased
```bash
PurchaseUtils.isPurchased(String idSubscribe);
```
