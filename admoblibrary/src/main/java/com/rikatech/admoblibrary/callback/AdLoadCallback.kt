package com.rikatech.admoblibrary.callback

interface AdLoadCallback {
    fun onAdFail(message: String?)
    fun onAdLoaded()
}
