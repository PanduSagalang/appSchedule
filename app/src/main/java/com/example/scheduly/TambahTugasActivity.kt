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
            etNamaTugas.setText(split[0])
            etMataKuliah.setText(split[1])
            etTanggal.setText(split[2])
            etCatatan.setText(split[3])

            btnSaveTugas.text = "Update Tugas"
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
            val nama = etNamaTugas.text.toString()
            val matkul = etMataKuliah.text.toString()
            val hari = etTanggal.text.toString()
            val catatan = etCatatan.text.toString()

            if (nama.isBlank() || matkul.isBlank() || hari.isBlank()) {
                Toast.makeText(this, "Semua data wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val newData = "$nama#$matkul#$hari#$catatan"


            if (oldData != null) {
                Storage.deleteTugas(this, oldData!!)
            }


            // Simpan ke Storage
            Storage.saveTugas(this, newData)


            Toast.makeText(this,
                if (oldData == null) "Tugas disimpan!" else "Tugas berhasil diupdate!",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }
}