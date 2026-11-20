package id.xods.wabae.hooks

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.log.YLog
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import java.util.Calendar
import java.util.Date

object ExpiryHook : YukiBaseHooker() {

    override fun onHook() {
        findAndHookExpiry()
    }

    private fun findAndHookExpiry() {
        runCatching {
            val date2099 = Calendar.getInstance().apply { set(2099, 11, 31) }.time

            val classLoader = appClassLoader ?: return
            val clazz = runCatching { classLoader.loadClass("com.whatsapp.util.WhatsAppLibChecking") }.getOrNull()
            clazz?.declaredMethods?.filter { it.returnType == Date::class.java }?.forEach { method ->
                XposedBridge.hookMethod(method, object : XC_MethodHook() {
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        param.result = date2099
                    }
                })
            }
            YLog.debug(msg = "WaBae: ExpiryHook registered successfully (Active until 2099)")
        }.onFailure {
            YLog.error(msg = "WaBae: Failed to hook expiry: ${it.message}")
        }
    }
}
