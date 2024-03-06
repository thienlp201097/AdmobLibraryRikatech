package com.rikatech.admoblibrary.callback;

public interface AdLoadCallback {
    void onAdFail(String message);
    void onAdLoaded();
}
