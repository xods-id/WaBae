package id.xods.wabae.hooks

import android.view.Window
import android.view.WindowManager
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.log.YLog
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import id.xods.wabae.data.PrefsManager

object MediaHook : YukiBaseHooker() {

    override fun onHook() {
        hookDisableFlagSecure()
    }

    /**
     * Izinkan screenshot & screen record di semua tampilan WA
     * Termasuk di foto/video sekali lihat (View Once)
     */
    private fun hookDisableFlagSecure() {
        runCatching {
            val windowClass = Window::class.java

            // Hook setFlags(int flags, int mask)
            runCatching {
                val setFlagsMethod = windowClass.getDeclaredMethod("setFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                XposedBridge.hookMethod(setFlagsMethod, object : XC_MethodHook() {
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        val ctx = appContext
                        val allowScreenshot = if (ctx != null) {
                            PrefsManager.getRemoteBoolean(ctx, PrefsManager.KEY_ANTI_VIEW_ONCE, true)
                        } else true

                        if (allowScreenshot) {
                            val flags = param.args[0] as Int
                            val mask = param.args[1] as Int
                            param.args[0] = flags and WindowManager.LayoutParams.FLAG_SECURE.inv()
                            param.args[1] = mask and WindowManager.LayoutParams.FLAG_SECURE.inv()
                        }
                    }
                })
            }

            // Hook addFlags(int flags)
            runCatching {
                val addFlagsMethod = windowClass.getDeclaredMethod("addFlags", Int::class.javaPrimitiveType)
                XposedBridge.hookMethod(addFlagsMethod, object : XC_MethodHook() {
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        val ctx = appContext
                        val allowScreenshot = if (ctx != null) {
                            PrefsManager.getRemoteBoolean(ctx, PrefsManager.KEY_ANTI_VIEW_ONCE, true)
                        } else true

                        if (allowScreenshot) {
                            val flags = param.args[0] as Int
                            val newFlags = flags and WindowManager.LayoutParams.FLAG_SECURE.inv()
                            param.args[0] = newFlags
                            if (newFlags == 0) {
                                param.result = null
                            }
                        }
                    }
                })
            }

            YLog.debug(msg = "WaBae: FLAG_SECURE bypass & Anti-ViewOnce hooked successfully")
        }.onFailure {
            YLog.error(msg = "WaBae: Failed to hook FLAG_SECURE: ${it.message}")
        }
    }
}
