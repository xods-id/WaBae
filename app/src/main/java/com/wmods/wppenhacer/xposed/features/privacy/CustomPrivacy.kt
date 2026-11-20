package com.wmods.wppenhacer.xposed.features.privacy

import com.wmods.wppenhacer.xposed.core.components.FMessageWpp
import org.json.JSONObject

object CustomPrivacy {
    fun getJSON(userJid: FMessageWpp.UserJid?): JSONObject = JSONObject()
}
