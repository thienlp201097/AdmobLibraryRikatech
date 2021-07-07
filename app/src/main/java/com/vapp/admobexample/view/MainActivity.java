package com.vapp.admobexample.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.ads.google.admobads.admobnative.GoogleENative;
import com.vapp.admobexample.R;
import com.vapp.admobexample.iap.IAPActivity;
import com.vapp.admoblibrary.Utils;
import com.vapp.admoblibrary.ads.AdCallback;
import com.vapp.admoblibrary.ads.AdmodUtils;
import com.vapp.admoblibrary.ads.AppOpenManager;

public class MainActivity extends AppCompatActivity {

    Button btn_LoadInter;
    Button btn_ShowInter;
    Button btn_LoadAndShowInter;
    Button btn_LoadAndShowReward;
    Button btn_LoadNative;
    Button btn_IAP;
    LinearLayout nativeAds;
    LinearLayout banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btn_LoadInter = findViewById(R.id.btn_LoadInter);
        btn_ShowInter = findViewById(R.id.btn_ShowInter);
        btn_LoadAndShowInter = findViewById(R.id.btn_LoadAndShowInter);
        btn_LoadAndShowReward = findViewById(R.id.btn_LoadAndShowReward);
        btn_LoadNative = findViewById(R.id.btn_LoadNative);
        nativeAds = findViewById(R.id.nativeAds);
        banner = findViewById(R.id.banner);
        btn_IAP = findViewById(R.id.btn_IAP);
        btn_LoadInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdmodUtils.getInstance().loadAdInterstitial(MainActivity.this, getString(R.string.ads_admob_inter_id), false);

            }
        });
        btn_ShowInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdmodUtils.getInstance().showAdInterstitialWithCallback(MainActivity.this, new AdCallback() {
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
                }, 0);
            }
        });
        btn_LoadAndShowInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
        btn_LoadAndShowReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

            }
        });
        btn_LoadNative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.getInstance().addActivity(MainActivity.this, NativeRecyclerActivity.class);
            }
        });
        btn_IAP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.getInstance().addActivity(MainActivity.this, IAPActivity.class);
            }
        });

        AdmodUtils.getInstance().loadNativeAds(MainActivity.this, getString(R.string.ads_admob_native_id), nativeAds, GoogleENative.UNIFIED_SMALL);
        AdmodUtils.getInstance().loadAdBanner(MainActivity.this, getString(R.string.ads_admob_native_id), banner);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}