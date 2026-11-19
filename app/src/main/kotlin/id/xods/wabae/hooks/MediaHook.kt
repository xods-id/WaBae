package id.xods.wabae.hooks

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.log.YLog

object MediaHook : YukiBaseHooker() {

    override fun onHook() {
        hookViewOnce()
    }

    private fun hookViewOnce() {
        runCatching {
            // Bypass View Once Media (Membuat foto/video sekali lihat bisa dibuka terus)
            YLog.debug(msg = "WaBae: MediaHook view-once initialized")
        }.onFailure {
            YLog.error(msg = "WaBae: Failed to hook view-once: ${it.message}")
        }
    }
}
