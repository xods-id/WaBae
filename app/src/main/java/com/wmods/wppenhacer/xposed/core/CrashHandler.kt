package com.wmods.wppenhacer.xposed.core

import android.app.Application
import com.wmods.wppenhacer.xposed.utils.YukiLog

internal object CrashHandler {
    private var installed = false

    fun install(application: Application, whatsAppVersion: String) {
        if (installed) return
        installed = true

        val previousHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            try {
                YukiLog.log(throwable)
            } finally {
                previousHandler?.uncaughtException(thread, throwable) ?: Runtime.getRuntime().exit(2)
            }
        }
    }
}
