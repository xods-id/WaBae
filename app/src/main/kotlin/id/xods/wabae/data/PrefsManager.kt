package id.xods.wabae.data

import android.content.Context

object PrefsManager {

    const val PREFS_NAME = "wabae_preferences"

    // Keys - Privacy
    const val KEY_GHOST_MODE = "ghost_mode"
    const val KEY_FREEZE_LAST_SEEN = "freeze_last_seen"
    const val KEY_HIDE_ONLINE = "hide_online"
    const val KEY_HIDE_READ = "hide_read"
    const val KEY_BLUE_ON_REPLY = "blue_on_reply"
    const val KEY_HIDE_DELIVERED = "hide_delivered"
    const val KEY_HIDE_TYPING = "hide_typing"
    const val KEY_HIDE_RECORDING = "hide_recording"
    const val KEY_GHOST_STATUS_VIEW = "ghost_status_view"
    const val KEY_ANTI_VIEW_ONCE = "anti_view_once"
    const val KEY_ANTI_DELETE = "anti_delete"
    const val KEY_BLOCK_UNKNOWN_CALLS = "block_unknown_calls"
    const val KEY_CALL_REJECT_TYPE = "call_reject_type" // no_internet, declined, busy

    // Keys - Media
    const val KEY_IMAGE_QUALITY = "image_quality"
    const val KEY_VIDEO_QUALITY = "video_quality"
    const val KEY_VIDEO_60FPS = "video_60fps"
    const val KEY_DOWNLOAD_STATUS = "download_status"
    const val KEY_DOWNLOAD_VIEW_ONCE = "download_view_once"
    const val KEY_DISABLE_PROXIMITY = "disable_proximity"
    const val KEY_MEDIA_PREVIEW = "media_preview"

    // Key mapping for hook engine compatibility
    private val keyMapping = mapOf(
        KEY_ANTI_VIEW_ONCE to listOf("viewonce"),
        KEY_ANTI_DELETE to listOf("antirevoke", "antirevokestatus"),
        KEY_HIDE_TYPING to listOf("ghostmode_t"),
        KEY_HIDE_RECORDING to listOf("ghostmode_r"),
        KEY_GHOST_MODE to listOf("ghostmode"),
        KEY_HIDE_READ to listOf("hideread"),
        KEY_GHOST_STATUS_VIEW to listOf("hidestatusview"),
        KEY_FREEZE_LAST_SEEN to listOf("freezelastseen"),
        KEY_BLOCK_UNKNOWN_CALLS to listOf("call_privacy"),
        KEY_DOWNLOAD_STATUS to listOf("downloadstatus"),
        KEY_DOWNLOAD_VIEW_ONCE to listOf("downloadviewonce"),
        KEY_IMAGE_QUALITY to listOf("imagequality"),
        KEY_VIDEO_QUALITY to listOf("videoquality"),
        KEY_MEDIA_PREVIEW to listOf("enable_media_preview")
    )

    fun getLocalBoolean(context: Context, key: String, defValue: Boolean = false): Boolean {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(key, defValue)
    }

    fun setLocalBoolean(context: Context, key: String, value: Boolean) {
        val editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        editor.putBoolean(key, value)

        // Sync with mapped hook keys
        keyMapping[key]?.forEach { aliasKey ->
            editor.putBoolean(aliasKey, value)
            if (aliasKey == "antirevoke" || aliasKey == "antirevokestatus" || aliasKey == "call_privacy") {
                editor.putString(aliasKey, if (value) "1" else "0")
            }
        }
        editor.apply()
    }

    fun getLocalString(context: Context, key: String, defValue: String = ""): String {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(key, defValue) ?: defValue
    }

    fun setLocalString(context: Context, key: String, value: String) {
        val editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        editor.putString(key, value)
        if (key == KEY_CALL_REJECT_TYPE) {
            editor.putString("call_type", value)
        }
        editor.apply()
    }
}
