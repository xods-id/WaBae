package id.xods.wabae

import android.annotation.SuppressLint
import android.app.Application
import android.app.Instrumentation
import android.view.Window
import android.view.WindowManager
import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.yukihookapi.YukiHook
import com.highcapable.yukihookapi.annotation.xposed.YukiHookLibXposedEntry
import com.highcapable.yukihookapi.extension.kavaref.hook
import com.highcapable.yukihookapi.extension.kavaref.hookAll
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.factory.injectModuleResources
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.xposed.YukiHookXposedModule
import com.wmods.wppenhacer.xposed.core.FeatureLoader

@YukiHookLibXposedEntry(
    minApiVersion = 101,
    targetApiVersion = 101,
    scope = ["com.whatsapp", "com.whatsapp.w4b"],
    hotReload = YukiHookLibXposedEntry.HotReload.NONE
)
class HookEntry : YukiHookXposedModule {

    override fun onInit() {
        YukiHook.configure {
            logging { tag = "WaBae" }
            dataChannel = true
        }
    }

    @SuppressLint("WorldReadableFiles")
    override fun onHook() = encase {
        loadApp(FeatureLoader.PACKAGE_WPP, FeatureLoader.PACKAGE_BUSINESS) {
            withProcess(mainProcessName) {
                disableSecureFlag(this)

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

    private fun disableSecureFlag(param: PackageParam) {
        param.apply {
            Window::class.resolve().apply {
                firstMethod {
                    name = "setFlags"
                    parameters(Int::class, Int::class)
                }.hook {
                    before {
                        args[0] = (args[0] as Int) and WindowManager.LayoutParams.FLAG_SECURE.inv()
                        args[1] = (args[1] as Int) and WindowManager.LayoutParams.FLAG_SECURE.inv()
                    }
                }

                firstMethod {
                    name = "addFlags"
                    parameters(Int::class)
                }.hook {
                    before {
                        val newFlags = (args[0] as Int) and WindowManager.LayoutParams.FLAG_SECURE.inv()
                        args[0] = newFlags
                        if (newFlags == 0) {
                            result = null
                        }
                    }
                }
            }
        }
    }
}
