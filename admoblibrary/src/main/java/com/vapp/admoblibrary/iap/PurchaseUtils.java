package com.vapp.admoblibrary.iap;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.vapp.admoblibrary.Utils;
import com.vapp.admoblibrary.ads.AdmodUtils;

public class PurchaseUtils {
    public static BillingProcessor bp;
    public static TransactionDetails purchaseTransactionDetails = null;
    public static boolean statusSubscriptioned = false;

    private static volatile PurchaseUtils INSTANCE;


    public static synchronized PurchaseUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PurchaseUtils();
        }
        return INSTANCE;
    }

    public  void initBilling(Context context, String play_console_license) {
        bp = new BillingProcessor(context, play_console_license, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(String productId, TransactionDetails details) {
                Utils.getInstance().showMessenger(context,"onProductPurchased");
            }

            @Override
            public void onPurchaseHistoryRestored() {
                Utils.getInstance().showMessenger(context,"onPurchaseHistoryRestored");

            }

            @Override
            public void onBillingError(int errorCode, Throwable error) {
                Utils.getInstance().showMessenger(context,"onBillingError");

            }

            @Override
            public void onBillingInitialized() {

            }
        });
        bp.initialize();
    }

    private  boolean hasSubscription() {
        if (purchaseTransactionDetails != null) {
            return purchaseTransactionDetails.purchaseInfo != null;
        }
        return false;
    }


    public  boolean isPurchased(String idSubscribe) {
        purchaseTransactionDetails = bp.getSubscriptionTransactionDetails(idSubscribe);
        bp.loadOwnedPurchasesFromGoogle();
        if (hasSubscription()) {
            return statusSubscriptioned = true;
        } else {
            return statusSubscriptioned = false;
        }
    }

    public  void subscribe(Activity context, String idSubscribe) {
        if (bp.isSubscriptionUpdateSupported()) {
            bp.subscribe(context, idSubscribe);
        } else {
            Log.d("MainActivity", "onBillingInitialized: Subscription updated is not supported");
        }
    }
}
