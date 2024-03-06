package com.rikatech.admoblibrary.ads;

public interface AdLoadCallback {
    void onAdFail(String message);
    void onAdLoaded();
}
