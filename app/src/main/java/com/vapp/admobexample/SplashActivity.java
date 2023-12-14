package com.vapp.admobexample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.ads.AdValue;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.vapp.admobexample.utilsdemp.AdsManager;
import com.vapp.admobexample.utilsdemp.RemoteConfigManager;
import com.vapp.admobexample.view.MainActivity;
import com.vapp.admobexample.view.OtherActivity;
import com.vapp.admoblibrary.ads.AOAManager;
import com.vapp.admoblibrary.ads.AdCallbackNew;
import com.vapp.admoblibrary.ads.AdLoadCallback;
import com.vapp.admoblibrary.ads.AppOpenManager;
import com.vapp.admoblibrary.utils.Utils;
import com.vapp.admoblibrary.ads.AdCallback;
import com.vapp.admoblibrary.ads.AdmodUtils;

public class SplashActivity extends AppCompatActivity {
    AOAManager aoaManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        if ((!isTaskRoot() && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)) && getIntent().getAction() != null && getIntent().getAction() == Intent.ACTION_MAIN) {
            finish();
            return;
        }

        aoaManager = new AOAManager(this, "ca-app-pub-3940256099942544/3419835294", 3000, new AOAManager.AppOpenAdsListener() {
            @Override
            public void onAdPaid(@NonNull AdValue adValue , String s) {
                Toast.makeText(SplashActivity.this, "${adValue?.currencyCode}|${adValue?.valueMicros}", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdsClose() {
                Utils.getInstance().replaceActivity(SplashActivity.this, MainActivity.class);
            }

            @Override
            public void onAdsFailed(String massage) {
                Utils.getInstance().replaceActivity(SplashActivity.this, MainActivity.class);
            }
        });
        aoaManager.loadAndShowAoA();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aoaManager.onAoaDestroyed();
    }
}


