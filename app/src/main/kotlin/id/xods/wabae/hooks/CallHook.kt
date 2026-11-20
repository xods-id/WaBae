package id.xods.wabae.hooks

import android.os.Message
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.log.YLog
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import id.xods.wabae.data.PrefsManager
import java.lang.reflect.Method

object CallHook : YukiBaseHooker() {

    override fun onHook() {
        hookCallBlocking()
    }

    private fun hookCallBlocking() {
        runCatching {
            val classLoader = appClassLoader ?: return
            val voipManagerClass = runCatching {
                classLoader.loadClass("com.whatsapp.voipcalling.Voip")
            }.getOrNull()

            if (voipManagerClass != null) {
                // Hook nativeHandleIncomingXmppOffer untuk tolak panggilan tanpa internet / reject
                voipManagerClass.declaredMethods.filter { 
                    it.name == "nativeHandleIncomingXmppOffer" || it.name == "handleIncomingXmppOffer" 
                }.forEach { method ->
                    XposedBridge.hookMethod(method, object : XC_MethodHook() {
                        override fun beforeHookedMethod(param: MethodHookParam) {
                            val ctx = appContext ?: return
                            val blockEnabled = PrefsManager.getRemoteBoolean(ctx, PrefsManager.KEY_BLOCK_UNKNOWN_CALLS, false)
                            if (blockEnabled) {
                                val callRejectType = PrefsManager.getRemoteString(ctx, PrefsManager.KEY_CALL_REJECT_TYPE, "no_internet")
                                if (callRejectType == "no_internet") {
                                    param.result = 1 // 1 = Ignore/No connection response
                                    YLog.debug(msg = "WaBae: Blocked incoming call with simulated no internet")
                                }
                            }
                        }
                    })
                }
            }
            YLog.debug(msg = "WaBae: CallHook initialized")
        }.onFailure {
            YLog.error(msg = "WaBae: Failed to hook call: ${it.message}")
        }
    }
}
