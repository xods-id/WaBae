package id.xods.wabae

import android.app.Application
import android.app.Instrumentation
import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.extension.kavaref.hookAll
import com.highcapable.yukihookapi.hook.factory.configs
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.factory.injectModuleResources
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import com.wmods.wppenhacer.xposed.core.FeatureLoader

@InjectYukiHookWithXposed
class HookEntry : IYukiHookXposedInit {

    override fun onInit() = configs {
        isDebug = true
    }

    override fun onHook() = encase {
        loadApp(FeatureLoader.PACKAGE_WPP, FeatureLoader.PACKAGE_BUSINESS) {
            withProcess(mainProcessName) {
                Instrumentation::class.resolve().method {
                    name = "callApplicationOnCreate"
                    parameters(Application::class)
                }.hookAll {
                    before {
                        val application = args[0] as Application
                        application.injectModuleResources()
                        loadHooker(FeatureLoader)
                        FeatureLoader.start(
                            hostClassLoader!!,
                            application,
                            appInfo.sourceDir!!
                        )
                    }
                }
            }
        }
    }
}
