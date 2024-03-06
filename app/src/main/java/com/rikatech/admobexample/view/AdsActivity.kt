package com.rikatech.admobexample.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rikatech.admobexample.R
import com.rikatech.admobexample.databinding.ActivityAdsBinding
import com.rikatech.admobexample.utilsdemp.AdsManager

class AdsActivity : AppCompatActivity() {
    val binding by lazy { ActivityAdsBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //InterstitialAd
        binding.loadInter.setOnClickListener {
            AdsManager.loadInter(this,AdsManager.inter_holder)
        }
        binding.showInter.setOnClickListener {
            AdsManager.showAdInter(this,AdsManager.inter_holder,object :AdsManager.AdListener{
                override fun onAdClosedOrFailed() {
                    startActivity(Intent(this@AdsActivity,OtherActivity::class.java))
                }
            })
        }
        binding.loadAndShowInter.setOnClickListener {
            AdsManager.loadAndShowIntersial(this,AdsManager.inter_holder,object :AdsManager.AdListener{
                override fun onAdClosedOrFailed() {
                    startActivity(Intent(this@AdsActivity,OtherActivity::class.java))
                }
            })
        }

        //Native
        binding.loadNative.setOnClickListener {
            AdsManager.loadNative(this,AdsManager.nativeHolder)
        }
        binding.showNative.setOnClickListener {
            AdsManager.showAdNative(this,binding.frNative,AdsManager.nativeHolder)
        }
        binding.loadAndShowNative.setOnClickListener {
            AdsManager.loadAndShowNative(this,binding.frNative,AdsManager.nativeHolder)
        }

        //Banner
        binding.banner.setOnClickListener {
            AdsManager.showAdBanner(this,"",binding.frNative,binding.frNative)
        }
        binding.bannerColap.setOnClickListener {
            AdsManager.showAdBannerCollapsible(this,"",binding.frNative,binding.frNative)
        }

        //Reward
        binding.loadReward.setOnClickListener {
            AdsManager.loadReward(this,AdsManager.interRewardHolder)
        }
        binding.showReward.setOnClickListener {
            AdsManager.showInterReward(this,AdsManager.interRewardHolder,object :AdsManager.AdListener{
                override fun onAdClosedOrFailed() {

                }
            })
        }
        binding.loadAndShowReward.setOnClickListener {
            AdsManager.loadAndShowReward(this,AdsManager.interRewardHolder,object :AdsManager.AdListener{
                override fun onAdClosedOrFailed() {

                }
            })
        }
    }
}