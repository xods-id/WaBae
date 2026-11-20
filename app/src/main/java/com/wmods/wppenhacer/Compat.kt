package com.wmods.wppenhacer

import android.app.Activity

object R {
    typealias string = id.xods.wabae.R.string
    typealias array = id.xods.wabae.R.array
    typealias style = id.xods.wabae.R.style
    typealias drawable = id.xods.wabae.R.drawable
}

object BuildConfig {
    const val APPLICATION_ID = "id.xods.wabae"
    const val VERSION_NAME = "1.0.0"
    const val RESET_ON_INSTALL = false
}

object App {
    const val isOriginalPackage = true
}

object Tasker {
    @JvmStatic
    fun sendTaskerEvent(vararg args: Any?) {}
}

class UpdateChecker(private val mActivity: Activity) : Runnable {
    override fun run() {
        // No-op for WaBae
    }
}
