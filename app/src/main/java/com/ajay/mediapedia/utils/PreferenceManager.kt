package com.ajay.mediapedia.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    private var mPreferences: SharedPreferences
            = context.getSharedPreferences("Mediapedia", Context.MODE_PRIVATE)
    private var mEditor: SharedPreferences.Editor = mPreferences.edit()

    fun setStringPref(key: String,value: String) {
        mEditor.putString(key, value)
        mEditor.commit()
    }

    fun getStringPref(key: String): String {
        return mPreferences.getString(key, "")!!
    }

    companion object {
        const val KEY_SPOTIFY_AUTH_TOKEN: String = "key_spotify_auth_token"
    }
}