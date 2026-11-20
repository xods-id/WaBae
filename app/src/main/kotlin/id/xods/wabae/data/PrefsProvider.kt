package id.xods.wabae.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.net.Uri
import android.os.Bundle

class PrefsProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "id.xods.wabae.prefs"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY")
        const val METHOD_GET_ALL = "getAll"
        const val METHOD_SET_BOOLEAN = "setBoolean"
        const val METHOD_SET_STRING = "setString"
        const val KEY_VALUES = "values"
        const val PREFS_NAME = "wabae_preferences"
    }

    private lateinit var prefs: SharedPreferences

    override fun onCreate(): Boolean {
        context?.let {
            prefs = it.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        }
        return true
    }

    override fun call(method: String, arg: String?, extras: Bundle?): Bundle? {
        val result = Bundle()
        when (method) {
            METHOD_GET_ALL -> {
                val bundle = Bundle()
                for ((key, value) in prefs.all) {
                    when (value) {
                        is Boolean -> bundle.putBoolean(key, value)
                        is Int -> bundle.putInt(key, value)
                        is Long -> bundle.putLong(key, value)
                        is Float -> bundle.putFloat(key, value)
                        is String -> bundle.putString(key, value)
                    }
                }
                result.putBundle(KEY_VALUES, bundle)
                return result
            }
            METHOD_SET_BOOLEAN -> {
                if (arg != null && extras != null) {
                    val value = extras.getBoolean("value")
                    prefs.edit().putBoolean(arg, value).apply()
                    result.putBoolean("success", true)
                    return result
                }
            }
            METHOD_SET_STRING -> {
                if (arg != null && extras != null) {
                    val value = extras.getString("value") ?: ""
                    prefs.edit().putString(arg, value).apply()
                    result.putBoolean("success", true)
                    return result
                }
            }
        }
        return null
    }

    override fun query(uri: Uri, p1: Array<String>?, p2: String?, p3: Array<String>?, p4: String?): Cursor? = null
    override fun getType(uri: Uri): String = ""
    override fun insert(uri: Uri, values: ContentValues?): Uri? = null
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int = 0
}
