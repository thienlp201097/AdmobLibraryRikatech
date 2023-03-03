package com.vapp.admoblibrary;

import com.google.android.gms.ads.AdValue;

public interface AdsInterCallBack {
    void onAdClosed();
    void onEventClickAdClosed();
    void onAdShowed();
    void onAdLoaded();
    void onAdFail();
    void onPaid(AdValue adValue);
}
