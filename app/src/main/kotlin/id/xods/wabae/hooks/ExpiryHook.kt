package id.xods.wabae.hooks

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.log.YLog
import java.util.Calendar
import java.util.Date

object ExpiryHook : YukiBaseHooker() {

    override fun onHook() {
        findAndHookExpiry()
    }

    private fun findAndHookExpiry() {
        runCatching {
            val date2099 = Calendar.getInstance().apply { set(2099, 11, 31) }.time

            // Scan classes for software_forced_expiration or Date methods in package com.whatsapp
            appClassLoader?.let { classLoader ->
                // Hook Date return types in WhatsApp expiration handlers
                runCatching {
                    val expirationClass = classLoader.loadClass("com.whatsapp.util.WhatsAppLibChecking")
                    expirationClass.declaredMethods.filter { it.returnType == Date::class.java }.forEach { m ->
                        m.hook {
                            before {
                                result = date2099
                            }
                        }
                    }
                }
            }
            YLog.debug(msg = "WaBae: ExpiryHook registered successfully (Active until 2099)")
        }.onFailure {
            YLog.error(msg = "WaBae: Failed to hook expiry: ${it.message}")
        }
    }
}
