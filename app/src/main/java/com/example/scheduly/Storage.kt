package com.example.scheduly

import android.content.Context

object Storage {

    private const val PREF_NAME = "SchedulyStorage"
    private const val KEY_TUGAS = "list_tugas"
    private const val KEY_JADWAL = "list_jadwal"


    //   TUGAS

    fun saveTugas(context: Context, data: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val existing = prefs.getString(KEY_TUGAS, "") ?: ""
        prefs.edit().putString(KEY_TUGAS, existing + data + ";").apply()
    }

    fun getTugas(context: Context): List<String> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val raw = prefs.getString(KEY_TUGAS, "") ?: ""
        return raw.split(";").filter { it.isNotBlank() }
    }


    //   JADWAL (
    fun saveJadwal(context: Context, data: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val existing = prefs.getString(KEY_JADWAL, "") ?: ""
        prefs.edit().putString(KEY_JADWAL, existing + data + ";").apply()
    }

    fun getJadwal(context: Context): List<String> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val raw = prefs.getString(KEY_JADWAL, "") ?: ""
        return raw.split(";").filter { it.isNotBlank() }
    }
}