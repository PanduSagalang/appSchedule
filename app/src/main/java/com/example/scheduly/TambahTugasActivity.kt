package com.example.scheduly

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class TambahTugasActivity : AppCompatActivity() {

    private var oldData: String? = null
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

        oldData = intent.getStringExtra("editData")
        if (oldData != null) {
            val split = oldData!!.split("#")
            if (split.size >= 5) {
                oldId = split[0].toLongOrNull()
                etNamaTugas.setText(split[1])
                etMataKuliah.setText(split[2])
                etTanggal.setText(split[3])
                etCatatan.setText(split[4])
            }
            btnSaveTugas.text = "Update"
        }

        etTanggal.setOnClickListener {
            val calendar = Calendar.getInstance()

            val datePicker = DatePickerDialog(
                this,
                { _, year, month, day ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, day)

                    val formatter = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
                    etTanggal.setText(formatter.format(selectedDate.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()
        }

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
                finish()
                return@setOnClickListener
            }

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
            finish()
        }
    }
}