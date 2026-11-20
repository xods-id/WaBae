package com.wmods.wppenhacer

import android.app.Activity

typealias R = id.xods.wabae.R
typealias BuildConfig = id.xods.wabae.BuildConfig

object App {
    const val isOriginalPackage = true
}

class UpdateChecker(private val mActivity: Activity) : Runnable {
    override fun run() {
        // No-op for WaBae
    }
}
