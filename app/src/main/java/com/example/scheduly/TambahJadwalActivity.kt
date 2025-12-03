package com.example.scheduly

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TambahJadwalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_jadwal)

        val etNamaKelas = findViewById<EditText>(R.id.etNamaKelas)
        val etCatatan = findViewById<EditText>(R.id.etCatatan)
        val etHari = findViewById<EditText>(R.id.etHariJadwal)
        val etRuang = findViewById<EditText>(R.id.etRuang)
        val etMulai = findViewById<EditText>(R.id.etStartTime)
        val etSelesai = findViewById<EditText>(R.id.etEndTime)
        val cbNotif = findViewById<CheckBox>(R.id.cbNotifJadwal)

        val btnClose = findViewById<ImageButton>(R.id.btnCloseJadwal)
        btnClose.setOnClickListener {
            finish()
        }

        val btnSave = findViewById<Button>(R.id.btnSaveJadwal)
        btnSave.setOnClickListener {
            val namaKelas = etNamaKelas.text.toString()
            val catatan = etCatatan.text.toString()
            val hari = etHari.text.toString()
            val ruang = etRuang.text.toString()
            val jamMulai = etMulai.text.toString()
            val jamSelesai = etSelesai.text.toString()
            val isNotif = cbNotif.isChecked

            if (namaKelas.isEmpty()) {
                etNamaKelas.error = "Nama kelas harus diisi!"
                return@setOnClickListener
            }

            val jadwalBaru = Jadwal(
                namaKelas = namaKelas,
                catatan = catatan,
                hari = hari,
                ruang = ruang,
                jamMulai = jamMulai,
                jamSelesai = jamSelesai,
                isNotifikasiAktif = isNotif
            )

            Toast.makeText(this, "Disimpan: ${jadwalBaru.namaKelas} di ${jadwalBaru.ruang}", Toast.LENGTH_LONG).show()

            finish()
        }
    }
}