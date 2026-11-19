package id.xods.wabae

import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.configs
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import id.xods.wabae.hooks.ExpiryHook
import id.xods.wabae.hooks.MediaHook
import id.xods.wabae.hooks.PrivacyHook

@InjectYukiHookWithXposed
class HookEntry : IYukiHookXposedInit {

    override fun onInit() = configs {
        isDebug = false
    }

    override fun onHook() = encase {
        loadApp(name = "com.whatsapp") {
            loadHooker(ExpiryHook)
            loadHooker(PrivacyHook)
            loadHooker(MediaHook)
        }
        loadApp(name = "com.whatsapp.w4b") {
            loadHooker(ExpiryHook)
            loadHooker(PrivacyHook)
            loadHooker(MediaHook)
        }
    }
}
