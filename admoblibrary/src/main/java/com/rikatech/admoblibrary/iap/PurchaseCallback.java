package com.rikatech.admoblibrary.iap;

public interface PurchaseCallback {
    void onSkuDetailsResponse(SkuDetailsModel model);
    void onSkuDetailsError(String error);
}
