package com.vapp.admobexample.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.codemybrainsout.ratingdialog.MaybeLaterCallback;
import com.codemybrainsout.ratingdialog.RatingDialog;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdValue;
import com.google.android.gms.ads.nativead.NativeAd;
import com.vapp.admobexample.utilsdemp.AdsManager;
import com.vapp.admobexample.utilsdemp.UtilsDemoActivity;
import com.vapp.admoblibrary.ads.AOAManager;
import com.vapp.admoblibrary.ads.AdCallbackNew;
import com.vapp.admoblibrary.ads.AdLoadCallback;
import com.vapp.admoblibrary.ads.NativeAdCallback;
import com.vapp.admoblibrary.ads.admobnative.enumclass.CollapsibleBanner;
import com.vapp.admoblibrary.ads.admobnative.enumclass.GoogleENative;
import com.vapp.admobexample.R;
import com.vapp.admobexample.iap.IAPActivity;
import com.vapp.admoblibrary.ads.model.AdUnitListModel;
import com.vapp.admoblibrary.ads.model.BannerAdCallback;
import com.vapp.admoblibrary.utils.Utils;
import com.vapp.admoblibrary.ads.AdmodUtils;
import com.vapp.admoblibrary.ads.RewardAdCallback;

public class MainActivity extends AppCompatActivity {
    Button btn_LoadInter, btn_ShowInter, btn_ShowInter1, btn_ShowInter2, btn_ShowInter3;
    Button btn_LoadAndShowInter, btn_LoadAndShowReward;
    Button btn_LoadInterReward, btn_ShowInterReward;
    Button btn_LoadNativeinRec, btn_LoadNativeGrid;
    Button btn_LoadAndShowNative, btn_LoadAndGetNative, btn_ShowNative;
    Button btn_IAP, btn_Rate, btn_Utils;
    FrameLayout viewNativeAds;
    FrameLayout banner;
    public NativeAd nativeAd;
    public NativeAd nativeAd2;
    public NativeAd nativeAd3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findbyid();
//        showDialogRate();

        AOAManager aoaManager = new AOAManager(this, "",10000, () -> Utils.getInstance().showMessenger(MainActivity.this, "onAdClosed"));
        aoaManager.showAdIfAvailable();
        // AdsConfigModel = Model call by API
//         Utils.getInstance().adUnitLists = adsConfigModel.getAdUnitList();

        //API data sample
        AdUnitListModel adUnitList = Utils.getInstance().getAdUnitByName("Name AdUnit", "Defaul Id Admob");
        //check Countries (BOOL)

//        if (Utils.getInstance().showAdForCountry(this,adUnitList)){
//            //show ads
//        }else{
//            //dont show ads
//        }

        btn_Utils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdsManager.INSTANCE.loadNative(MainActivity.this,AdsManager.INSTANCE.getNativeHolder());
            }
        });
        btn_LoadInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdsManager.INSTANCE.loadInter(MainActivity.this,AdsManager.INSTANCE.getInterholder());
            }
        });
        btn_ShowInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdsManager.INSTANCE.showAdInter(MainActivity.this, AdsManager.INSTANCE.getInterholder(), new AdsManager.AdListener() {
                    @Override
                    public void onAdClosed() {
                        Utils.getInstance().addActivity(MainActivity.this, OtherActivity.class);
                    }

                    @Override
                    public void onFailed() {
                        Utils.getInstance().addActivity(MainActivity.this, OtherActivity.class);
                    }
                });
            }
        });

        btn_ShowInter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdsManager.INSTANCE.showAdInter(MainActivity.this, AdsManager.INSTANCE.getInterholder(), new AdsManager.AdListener() {

                    @Override
                    public void onFailed() {
                        Utils.getInstance().addActivity(MainActivity.this, OtherActivity.class);
                    }

                    @Override
                    public void onAdClosed() {
                        Utils.getInstance().addActivity(MainActivity.this, OtherActivity.class);
                    }
                });
            }
        });
        btn_LoadAndShowInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdsManager.INSTANCE.loadAndShowIntersial(MainActivity.this, new AdsManager.AdListener() {
                    @Override
                    public void onAdClosed() {
                        startActivity(new Intent(MainActivity.this, OtherActivity.class));
                    }

                    @Override
                    public void onFailed() {
                        startActivity(new Intent(MainActivity.this, OtherActivity.class));
                    }
                });
            }
        });
        btn_LoadAndShowReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdmodUtils.loadAndShowAdRewardWithCallback(MainActivity.this, getString(R.string.test_ads_admob_reward_id), new RewardAdCallback() {
                    @Override
                    public void onAdClosed() {
                        if (AdmodUtils.mRewardedAd != null) {
                            AdmodUtils.mRewardedAd = null;
                        }
                        AdmodUtils.dismissAdDialog();
                        //Utils.getInstance().showMessenger(MainActivity.this, "close ad");
                        startActivity(new Intent(MainActivity.this, OtherActivity.class));
                    }

                    @Override
                    public void onEarned() {
                        if (AdmodUtils.mRewardedAd != null) {
                            AdmodUtils.mRewardedAd = null;
                        }
                        AdmodUtils.dismissAdDialog();
                        Utils.getInstance().showMessenger(MainActivity.this, "Reward");

                    }

                    @Override
                    public void onAdFail() {
                        //code here
                        Utils.getInstance().showMessenger(MainActivity.this, "Reward fail");
                    }
                }, true);

            }
        });
        btn_LoadNativeinRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.getInstance().addActivity(MainActivity.this, NativeRecyclerActivity.class);
            }
        });
        btn_LoadNativeGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().addActivity(MainActivity.this, NativeGridActivity.class);
            }
        });
        btn_LoadInterReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdmodUtils.loadAdInterstitialReward(MainActivity.this, getString(R.string.test_ads_admob_inter_reward_id), new AdLoadCallback() {
                    @Override
                    public void onAdFail() {
                        Utils.getInstance().addActivity(MainActivity.this, OtherActivity.class);
                        Utils.getInstance().showMessenger(MainActivity.this, "onAdFail");

                    }

                    @Override
                    public void onAdLoaded() {
                        Utils.getInstance().showMessenger(MainActivity.this, "show popup");
                        //show dialog
                    }
                });
            }

        });

        btn_ShowInterReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AdmodUtils.mInterstitialRewardAd != null) {
                    AdmodUtils.showAdInterstitialRewardWithCallback(AdmodUtils.mInterstitialRewardAd, MainActivity.this, new RewardAdCallback() {
                        @Override
                        public void onAdClosed() {
                            Utils.getInstance().addActivity(MainActivity.this, OtherActivity.class);
                        }

                        @Override
                        public void onAdFail() {
                            Utils.getInstance().showMessenger(MainActivity.this, "onAdFail");
                            Utils.getInstance().addActivity(MainActivity.this, OtherActivity.class);
                        }

                        @Override
                        public void onEarned() {
                            Utils.getInstance().showMessenger(MainActivity.this, "onEarned");
                            //bool true
                        }
                    });
                } else {
                    Utils.getInstance().replaceActivity(MainActivity.this, OtherActivity.class);

                }
            }
        });

        btn_IAP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.getInstance().addActivity(MainActivity.this, IAPActivity.class);
            }
        });
        btn_Rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogRate();
            }
        });
//        AdmodUtils.getInstance().loadNativeAdsWithLayout(MainActivity.this, getString(R.string.test_ads_admob_native_id), nativeAds, R.layout.ad_unified_medium);

        btn_LoadAndShowNative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdmodUtils.loadAndShowNativeAdsWithLayout(MainActivity.this, getString(R.string.test_ads_admob_native_id), viewNativeAds, R.layout.ad_template_medium, GoogleENative.UNIFIED_MEDIUM, new NativeAdCallback() {
                    @Override
                    public void onNativeAdLoaded() {
                    }

                    @Override
                    public void onAdFail(String s) {

                    }

                    @Override
                    public void onAdPaid(AdValue adValue) {
                        Utils.getInstance().showMessenger(MainActivity.this, adValue.toString());
                    }

                    @Override
                    public void onLoadedAndGetNativeAd(NativeAd ad) {

                    }
                });

            }
        });

        btn_LoadAndGetNative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdsManager.INSTANCE.loadNative(MainActivity.this,AdsManager.INSTANCE.getNativeHolder());
            }
        });

        btn_ShowNative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdsManager.INSTANCE.showAdNativeWithSize(MainActivity.this,viewNativeAds,AdsManager.INSTANCE.getNativeHolder());
            }
        });

//        AdmodUtils.getInstance().loadAdBanner(MainActivity.this, getString(R.string.test_ads_admob_banner_id), banner, new AdmodUtils.BannerCallBack() {
//            @Override
//            public void onLoad() {
//                Utils.getInstance().showMessenger(MainActivity.this, "onLoad");
//            }
//
//            @Override
//            public void onFailed() {
//                Utils.getInstance().showMessenger(MainActivity.this, "onFailed");
//            }
//
//            @Override
//            public void onPaid(AdValue adValue) {
//                Utils.getInstance().showMessenger(MainActivity.this, adValue.toString());
//            }
//        });
//        AdmodUtils.getInstance().loadAdBannerCollapsible(MainActivity.this, getString(R.string.test_ads_admob_banner_id), CollapsibleBanner.BOTTOM, banner, new BannerAdCallback() {
//            @Override
//            public void onBannerAdLoaded(AdSize adSize) {
//                Toast.makeText(MainActivity.this, String.valueOf(adSize.getHeight()), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAdFail() {
//
//            }
//        });
    }

    private void showDialogRate() {
        RatingDialog ratingDialog = new RatingDialog.Builder(this)
                .session(1)
                .date(1)
                .setNameApp(getString(R.string.app_name))
                .setIcon(R.mipmap.ic_launcher)
                .setEmail("vapp.helpcenter@gmail.com")
                .isShowButtonLater(true)
                .isClickLaterDismiss(true)
                .setTextButtonLater("Maybe Later")
                .setOnlickMaybeLate(new MaybeLaterCallback() {
                    @Override
                    public void onClick() {
                        Utils.getInstance().showMessenger(MainActivity.this, "clicked Maybe Later");
                    }
                })
                .ignoreRated(true)
                .ratingButtonColor(R.color.purple_200)
                .build();

        //Cancel On Touch Outside
        ratingDialog.setCanceledOnTouchOutside(false);
        //show
        ratingDialog.show();


        //thêm vào activity trong manifest
//        <intent-filter>
//            <action android:name="android.intent.action.SENDTO" />
//            <data android:scheme="mailto" />
//            <category android:name="android.intent.category.DEFAULT" />
//        </intent-filter>

        // thêm vào activity
//        android:windowSoftInputMode="adjustPan|adjustResize"

    }

    void findbyid() {
        btn_Utils = findViewById(R.id.btn_Utils);
        btn_LoadInter = findViewById(R.id.btn_LoadInter);
        btn_ShowInter = findViewById(R.id.btn_ShowInter);
        btn_ShowInter1 = findViewById(R.id.btn_ShowInter1);
        btn_LoadAndShowInter = findViewById(R.id.btn_LoadAndShowInter);
        btn_LoadAndShowReward = findViewById(R.id.btn_LoadAndShowReward);
        btn_LoadNativeinRec = findViewById(R.id.btn_LoadNative);
        viewNativeAds = findViewById(R.id.nativeAds);
        banner = findViewById(R.id.banner);
        btn_IAP = findViewById(R.id.btn_IAP);
        btn_Rate = findViewById(R.id.btn_Rate);
        btn_LoadNativeGrid = findViewById(R.id.btn_LoadNativeGrid);

        btn_LoadInterReward = findViewById(R.id.btn_LoadInterReward);
        btn_ShowInterReward = findViewById(R.id.btn_ShowInterReward);

        btn_LoadAndGetNative = findViewById(R.id.btn_LoadAndGetNative);
        btn_ShowNative = findViewById(R.id.btn_ShowNative);
        btn_LoadAndShowNative = findViewById(R.id.btn_LoadAndShowNative);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}