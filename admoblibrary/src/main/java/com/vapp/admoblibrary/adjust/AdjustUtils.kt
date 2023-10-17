package com.vapp.admoblibrary.adjust

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.LogLevel

object AdjustUtils {
    val ENVIRONMENT_PRODUCTION = AdjustConfig.ENVIRONMENT_PRODUCTION
    val ENVIRONMENT_SANDBOX = AdjustConfig.ENVIRONMENT_SANDBOX
    @JvmStatic
    fun initAdjust(application: Application,type : String,key : String) {
        val config = AdjustConfig(application, key,type)
        config.setLogLevel(LogLevel.WARN)
        Adjust.onCreate(config)
        application.registerActivityLifecycleCallbacks(AdjustLifecycleCallbacks())
    }

    class AdjustLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {}
        override fun onActivityStarted(activity: Activity) {}
        override fun onActivityResumed(activity: Activity) {
            Adjust.onResume()
        }
        override fun onActivityPaused(activity: Activity) {
            Adjust.onPause()
        }
        override fun onActivityStopped(activity: Activity) {}
        override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}
        override fun onActivityDestroyed(activity: Activity) {}
    }


}