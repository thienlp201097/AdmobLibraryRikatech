package com.rikatech.admobexample.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rikatech.admobexample.databinding.ActivityOtherBinding
import com.rikatech.admobexample.utilsdemp.AdsManager
import com.rikatech.admobexample.utilsdemp.AdsManager.nativeHolder

class OtherActivity : AppCompatActivity() {
    var binding: ActivityOtherBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtherBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        setResult(5)
        AdsManager.loadAndShowNativeFullScreen(this,binding!!.nativeAds,nativeHolder)
    }

}