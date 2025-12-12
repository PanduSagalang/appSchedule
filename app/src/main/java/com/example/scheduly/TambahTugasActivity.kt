package com.example.scheduly

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class TambahTugasActivity : AppCompatActivity() {

    private var oldId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_tugas)

        val btnClose = findViewById<ImageButton>(R.id.btnCloseTugas)
        val btnSaveTugas = findViewById<Button>(R.id.btnSaveTugas)
        val etNamaTugas = findViewById<EditText>(R.id.etNamaTugas)
        val etMataKuliah = findViewById<EditText>(R.id.etMataKuliah)
        val etTanggal = findViewById<EditText>(R.id.etTanggal)
        val etCatatan = findViewById<EditText>(R.id.etCatatan)

        btnClose.setOnClickListener { finish() }

        // 🔹 AMBIL ID JIKA MODE EDIT
        oldId = intent.getLongExtra("tugasId", -1L)
        if (oldId == -1L) oldId = null

        // 🔹 JIKA EDIT → AMBIL DATA DARI STORAGE
        oldId?.let { id ->
            val tugas = Storage.getTugasById(this, id)
            if (tugas != null) {
                etNamaTugas.setText(tugas.namaTugas)
                etMataKuliah.setText(tugas.mataKuliah)
                etTanggal.setText(tugas.hari)
                etCatatan.setText(tugas.catatan)
                btnSaveTugas.text = "Update"
            }
        }

        // DATE PICKER
        etTanggal.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    val selectedDate = Calendar.getInstance().apply {
                        set(year, month, day)
                    }
                    val formatter =
                        SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
                    etTanggal.setText(formatter.format(selectedDate.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // SAVE
        btnSaveTugas.setOnClickListener {

            val nama = etNamaTugas.text.toString().trim()
            val matkul = etMataKuliah.text.toString().trim()
            val hari = etTanggal.text.toString().trim()
            val catatan = etCatatan.text.toString().trim()

            if (nama.isBlank() || matkul.isBlank() || hari.isBlank()) {
                Toast.makeText(this, "Semua data wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (oldId != null) {
                val tugasUpdate = Tugas(
                    id = oldId!!,
                    namaTugas = nama,
                    mataKuliah = matkul,
                    hari = hari,
                    catatan = catatan,
                    isNotifikasiAktif = false
                )
                Storage.updateTugasObj(this, tugasUpdate)
                Toast.makeText(this, "Tugas berhasil diupdate!", Toast.LENGTH_SHORT).show()
            } else {
                val tugasBaru = Tugas(
                    id = System.currentTimeMillis(),
                    namaTugas = nama,
                    mataKuliah = matkul,
                    hari = hari,
                    catatan = catatan,
                    isNotifikasiAktif = false
                )
                Storage.saveTugasObj(this, tugasBaru)
                Toast.makeText(this, "Tugas disimpan!", Toast.LENGTH_SHORT).show()
            }
            val result = Intent()
            setResult(RESULT_OK, result)
            finish()
        }
    }
}
