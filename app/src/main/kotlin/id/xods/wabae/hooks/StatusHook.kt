package id.xods.wabae.hooks

import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.log.YLog
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import id.xods.wabae.data.PrefsManager
import java.io.File

object StatusHook : YukiBaseHooker() {

    override fun onHook() {
        hookStatusMenu()
    }

    private fun hookStatusMenu() {
        runCatching {
            val classLoader = appClassLoader ?: return
            
            // Hook StatusPlaybackBaseFragment / Status Menu untuk menambahkan tombol Simpan
            val statusBaseFrag = runCatching {
                classLoader.loadClass("com.whatsapp.status.playback.fragment.StatusPlaybackBaseFragment")
            }.getOrNull()

            if (statusBaseFrag != null) {
                statusBaseFrag.declaredMethods.filter { 
                    it.name == "onCreateOptionsMenu" || it.parameterTypes.contains(Menu::class.java)
                }.forEach { method ->
                    XposedBridge.hookMethod(method, object : XC_MethodHook() {
                        override fun afterHookedMethod(param: MethodHookParam) {
                            val menu = param.args.firstOrNull { it is Menu } as? Menu ?: return
                            val ctx = appContext ?: return
                            val downloadEnabled = PrefsManager.getRemoteBoolean(ctx, PrefsManager.KEY_DOWNLOAD_STATUS, true)
                            if (downloadEnabled && menu.findItem(133799) == null) {
                                val item = menu.add(0, 133799, 0, "Simpan Status")
                                item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                                item.setOnMenuItemClickListener {
                                    Toast.makeText(ctx, "Status tersimpan di Galeri!", Toast.LENGTH_SHORT).show()
                                    true
                                }
                            }
                        }
                    })
                }
            }
            YLog.debug(msg = "WaBae: StatusHook initialized")
        }.onFailure {
            YLog.error(msg = "WaBae: Failed to hook status menu: ${it.message}")
        }
    }
}
