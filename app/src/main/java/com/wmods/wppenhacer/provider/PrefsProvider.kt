package com.wmods.wppenhacer.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle

class PrefsProvider : ContentProvider() {

    companion object {
        const val METHOD_GET_ALL = "getAll"
        const val KEY_VALUES = "values"
        const val KEY_SETS = "sets"
        const val PREFS_NAME = "wabae_preferences"
    }

    override fun onCreate(): Boolean = true

    override fun call(method: String, arg: String?, extras: Bundle?): Bundle? {
        if (method != METHOD_GET_ALL) return null
        val ctx = context ?: return null
        val prefs = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val values = Bundle()
        val sets = Bundle()
        for ((key, value) in prefs.all) {
            when (value) {
                is Boolean -> values.putBoolean(key, value)
                is Int -> values.putInt(key, value)
                is Long -> values.putLong(key, value)
                is Float -> values.putFloat(key, value)
                is String -> values.putString(key, value)
                is Set<*> -> sets.putStringArrayList(key, ArrayList(value.filterIsInstance<String>()))
            }
        }
        return Bundle().apply {
            putBundle(KEY_VALUES, values)
            putBundle(KEY_SETS, sets)
        }
    }

    override fun query(
        uri: Uri,
        projection: Array<String?>?,
        selection: String?,
        selectionArgs: Array<String?>?,
        sortOrder: String?
    ): Cursor? = null

    override fun getType(uri: Uri): String = ""

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String?>?): Int = 0

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String?>?
    ): Int = 0
}
