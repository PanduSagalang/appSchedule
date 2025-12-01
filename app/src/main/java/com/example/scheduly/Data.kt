package com.example.scheduly

data class Tugas(
    val namaTugas: String,
    val mataKuliah: String,
    val hari: String,
    val catatan: String,
    val isNotifikasiAktif: Boolean,
    var status: StatusTugas
)

data class Jadwal(
    val namaKelas: String,
    val catatan: String,
    val hari: String,
    val ruang: String,
    val jamMulai: String,
    val jamSelesai: String,
    val isNotifikasiAktif: Boolean
)

enum class StatusTugas {
    Selesai,
    Belum_Selesai
}