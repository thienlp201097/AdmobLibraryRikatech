package com.rikatech.admoblibrary;

import com.google.android.gms.ads.AdValue;

public interface AdsInterCallBack {
    void onStartAction();
    void onEventClickAdClosed();
    void onAdShowed();
    void onAdLoaded();
    void onAdFail(String error);
    void onPaid(AdValue adValue, String adUnitAds);
}
