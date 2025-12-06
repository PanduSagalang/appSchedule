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

    fun editTugas(context: Context, id: String, nama: String, matkul: String, hari: String, catatan: String) {
        val shared = context.getSharedPreferences("tugas", Context.MODE_PRIVATE)
        val list = shared.getStringSet("list", mutableSetOf())!!.toMutableSet()

        val baru = list.map {
            if (it.startsWith("$id#")) "$id#$nama#$matkul#$hari#$catatan"
            else it
        }.toMutableSet()

        shared.edit().putStringSet("list", baru).apply()
    }

    fun deleteTugas(context: Context, target: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val raw = prefs.getString(KEY_TUGAS, "") ?: ""

        val listBaru = raw
            .split(";")
            .filter { it.isNotBlank() && it != target }
            .joinToString(";")

        prefs.edit().putString(KEY_TUGAS, listBaru + ";").apply()
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

    fun getJadwalList(context: Context): MutableList<Jadwal> {
        val rawList = getJadwal(context)   //
        val result = mutableListOf<Jadwal>()

        for (item in rawList) {
            val s = item.split("#")
            if (s.size >= 7) {
                try {
                    val jadwal = Jadwal(
                        id = s[0].toLong(),
                        namaKelas = s[1],
                        catatan = s[2],
                        hari = s[3],
                        ruang = s[4],
                        jamMulai = s[5],
                        jamSelesai = s[6],
                        isNotifikasiAktif = false
                    )
                    result.add(jadwal)
                } catch (_: Exception) {}
            }
        }
        return result
    }

    fun saveJadwalObj(context: Context, jadwal: Jadwal) {
        val data = jadwalToString(jadwal)
        saveJadwal(context, data)
    }

    fun updateJadwalObj(context: Context, jadwalBaru: Jadwal) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val raw = prefs.getString(KEY_JADWAL, "") ?: ""
        val list = raw.split(";").filter { it.isNotBlank() }

        val baru = list.map {
            val id = it.split("#").firstOrNull()
            if (id == jadwalBaru.id.toString()) jadwalToString(jadwalBaru) else it
        }

        prefs.edit().putString(KEY_JADWAL, baru.joinToString(";") + ";").apply()
    }

    fun deleteJadwalObj(context: Context, id: Long) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val raw = prefs.getString(KEY_JADWAL, "") ?: ""
        val list = raw.split(";").filter { it.isNotBlank() }

        val baru = list.filterNot {
            it.split("#").firstOrNull() == id.toString()
        }

        prefs.edit().putString(KEY_JADWAL, baru.joinToString(";") + ";").apply()
    }

    private fun jadwalToString(j: Jadwal): String {
        return "${j.id}#${j.namaKelas}#${j.catatan}#${j.hari}#${j.ruang}#${j.jamMulai}#${j.jamSelesai}"
    }


}