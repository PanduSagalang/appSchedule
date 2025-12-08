package com.example.scheduly

import android.content.Context

object Storage {

    private const val PREF_NAME = "SchedulyStorage"
    private const val KEY_TUGAS = "list_tugas"
    private const val KEY_JADWAL = "list_jadwal"

    //   TUGAS

    fun saveTugasObj(context: Context, tugas: Tugas) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        val existing = getTugasList(context)

        val updated = existing.toMutableList()
        updated.add(tugas)

        val data = updated.joinToString(";") { tugasToString(it) }

        prefs.edit().putString(KEY_TUGAS, data).apply()
    }

    fun getTugasList(context: Context): MutableList<Tugas> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val raw = prefs.getString(KEY_TUGAS, "") ?: ""

        if (raw.isBlank()) return mutableListOf()

        val list = raw.split(";")
        val result = mutableListOf<Tugas>()

        for (item in list) {
            val s = item.split("#")
            if (s.size >= 6) {
                result.add(
                    Tugas(
                        id = s[0].toLong(),
                        namaTugas = s[1],
                        mataKuliah = s[2],
                        hari = s[3],
                        catatan = s[4],
                        isNotifikasiAktif = false
                    )
                )
            }
        }

        return result
    }

    fun deleteTugasObj(context: Context, id: Long) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val list = getTugasList(context)

        val filtered = list.filter { it.id != id }

        val data = filtered.joinToString(";") { tugasToString(it) }
        prefs.edit().putString(KEY_TUGAS, data).apply()
    }
    fun updateTugasObj(context: Context, tugasBaru: Tugas) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val raw = prefs.getString(KEY_TUGAS, "") ?: ""
        val list = raw.split(";").filter { it.isNotBlank() }

        val updated = list.map {
            val s = it.split("#")
            if (s.isNotEmpty() && s[0] == tugasBaru.id.toString()) {
                tugasToString(tugasBaru)
            } else {
                it
            }
        }

        prefs.edit().putString(KEY_TUGAS, updated.joinToString(";") + ";").apply()
    }

    private fun tugasToString(t: Tugas): String {
        return "${t.id}#${t.namaTugas}#${t.mataKuliah}#${t.hari}#${t.catatan}#${t.isNotifikasiAktif}"
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