package id.xods.wabae

import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.configs
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import id.xods.wabae.hooks.CallHook
import id.xods.wabae.hooks.ExpiryHook
import id.xods.wabae.hooks.MediaHook
import id.xods.wabae.hooks.PrivacyHook
import id.xods.wabae.hooks.StatusHook

@InjectYukiHookWithXposed
class HookEntry : IYukiHookXposedInit {

    override fun onInit() = configs {
        isDebug = false
    }

    override fun onHook() = encase {
        loadApp(name = "com.whatsapp") {
            loadHooker(ExpiryHook)
            withProcess(mainProcessName) {
                loadHooker(PrivacyHook)
                loadHooker(MediaHook)
                loadHooker(CallHook)
                loadHooker(StatusHook)
            }
        }
        loadApp(name = "com.whatsapp.w4b") {
            loadHooker(ExpiryHook)
            withProcess(mainProcessName) {
                loadHooker(PrivacyHook)
                loadHooker(MediaHook)
                loadHooker(CallHook)
                loadHooker(StatusHook)
            }
        }
    }
}
