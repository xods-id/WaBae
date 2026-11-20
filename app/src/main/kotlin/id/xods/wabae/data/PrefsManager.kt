package id.xods.wabae.data

import android.content.Context
import android.os.Bundle
import id.xods.wabae.data.PrefsProvider.Companion.CONTENT_URI
import id.xods.wabae.data.PrefsProvider.Companion.KEY_VALUES
import id.xods.wabae.data.PrefsProvider.Companion.METHOD_GET_ALL
import id.xods.wabae.data.PrefsProvider.Companion.PREFS_NAME

object PrefsManager {

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

    // UI Helper (Local app process)
    fun getLocalBoolean(context: Context, key: String, defValue: Boolean = false): Boolean {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(key, defValue)
    }

    fun setLocalBoolean(context: Context, key: String, value: Boolean) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(key, value)
            .apply()
    }

    fun getLocalString(context: Context, key: String, defValue: String = ""): String {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(key, defValue) ?: defValue
    }

    fun setLocalString(context: Context, key: String, value: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(key, value)
            .apply()
    }

    // Hook Helper (WhatsApp process - calls ContentProvider)
    private var cachedRemotePrefs: Bundle? = null
    private var lastFetchTime = 0L

    fun getRemoteBoolean(context: Context, key: String, defValue: Boolean = false): Boolean {
        fetchRemotePrefs(context)
        return cachedRemotePrefs?.getBoolean(key, defValue) ?: defValue
    }

    fun getRemoteString(context: Context, key: String, defValue: String = ""): String {
        fetchRemotePrefs(context)
        return cachedRemotePrefs?.getString(key, defValue) ?: defValue
    }

    private fun fetchRemotePrefs(context: Context) {
        val now = System.currentTimeMillis()
        if (cachedRemotePrefs == null || now - lastFetchTime > 3000) {
            try {
                val bundle = context.contentResolver.call(CONTENT_URI, METHOD_GET_ALL, null, null)
                cachedRemotePrefs = bundle?.getBundle(KEY_VALUES)
                lastFetchTime = now
            } catch (e: Exception) {
                // fallback
            }
        }
    }
}
