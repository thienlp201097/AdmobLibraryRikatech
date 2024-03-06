package com.rikatech.admobexample.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codemybrainsout.ratingdialog.RatingDialog
import com.rikatech.admobexample.R
import com.rikatech.admobexample.databinding.ActivityOtherBinding
import com.rikatech.admobexample.utilsdemp.AdsManager
import com.rikatech.admobexample.utilsdemp.AdsManager.loadAndShowNative
import com.rikatech.admobexample.utilsdemp.AdsManager.loadAndShowNativeFullScreen
import com.rikatech.admobexample.utilsdemp.AdsManager.nativeHolder
import com.rikatech.admobexample.utilsdemp.AdsManager.showAdBanner
import com.rikatech.admobexample.utilsdemp.AdsManager.showAdBannerCollapsible
import com.rikatech.admoblibrary.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OtherActivity : AppCompatActivity() {
    var binding: ActivityOtherBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtherBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        setResult(5)
        AdsManager.loadAndShowNativeFullScreen(this,binding!!.nativeAds,nativeHolder)
        //        if (AdmodUtils.getInstance().dialog != null) {
//            if (AdmodUtils.getInstance().dialog.isShowing()) {
//                AdmodUtils.getInstance().dialog.dismiss();
//            }
//        }
        val ratingDialog = RatingDialog.Builder(this)
            .session(1)
            .date(1)
            .setNameApp(getString(R.string.app_name))
            .setIcon(R.mipmap.ic_launcher)
            .setEmail("vapp.helpcenter@gmail.com")
            .isShowButtonLater(true)
            .isClickLaterDismiss(true)
            .setTextButtonLater("Maybe Later")
            .setOnlickMaybeLate {
                Utils.getInstance().showMessenger(this@OtherActivity, "clicked Maybe Later")
            }
            .ignoreRated(true)
            .ratingButtonColor(R.color.purple_200)
            .build()

        //Cancel On Touch Outside
        ratingDialog.setCanceledOnTouchOutside(false)
        //show
//        ratingDialog.show();
    }

}