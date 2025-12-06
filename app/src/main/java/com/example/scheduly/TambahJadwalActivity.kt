package com.example.scheduly

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class TambahJadwalActivity : AppCompatActivity() {

    private var oldData: String? = null
    private var oldId: Long = -1L

    private val localeID = Locale("id", "ID")
    private val sdfTanggal = SimpleDateFormat("EEEE, dd MMMM yyyy", localeID)

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
        val btnSave = findViewById<Button>(R.id.btnSaveJadwal)

        btnClose.setOnClickListener { finish() }

        oldData = intent.getStringExtra("editData")
        if (oldData != null) {
            val split = oldData!!.split("#")
            if (split.size >= 7) {
                oldId = split[0].toLong()
                etNamaKelas.setText(split[1])
                etCatatan.setText(split[2])
                etHari.setText(split[3])
                etRuang.setText(split[4])
                etMulai.setText(split[5])
                etSelesai.setText(split[6])
            }
            btnSave.text = "Update"
        }


        etHari.inputType = 0
        etHari.isFocusable = false
        etHari.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val selected = Calendar.getInstance()
                    selected.set(year, month, dayOfMonth)
                    etHari.setText(sdfTanggal.format(selected.time))
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        setupTimePicker(etMulai)
        setupTimePicker(etSelesai)


        btnSave.setOnClickListener {
            val namaKelas = etNamaKelas.text.toString().trim()
            val catatan = etCatatan.text.toString().trim()
            val hari = etHari.text.toString().trim()
            val ruang = etRuang.text.toString().trim()
            val jamMulai = etMulai.text.toString().trim()
            val jamSelesai = etSelesai.text.toString().trim()
            val isNotif = cbNotif.isChecked

            if (namaKelas.isBlank() || hari.isBlank() || ruang.isBlank()
                || jamMulai.isBlank() || jamSelesai.isBlank()
            ) {
                Toast.makeText(this, "Semua data wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id = if (oldData == null) System.currentTimeMillis() else oldId

            val jadwalBaru = Jadwal(
                id = id,
                namaKelas = namaKelas,
                catatan = catatan,
                hari = hari,
                ruang = ruang,
                jamMulai = jamMulai,
                jamSelesai = jamSelesai,
                isNotifikasiAktif = isNotif
            )

            if (oldData == null) {
                Storage.saveJadwalObj(this, jadwalBaru)
                Toast.makeText(this, "Jadwal disimpan!", Toast.LENGTH_SHORT).show()
            } else {
                Storage.updateJadwalObj(this, jadwalBaru)
                Toast.makeText(this, "Jadwal berhasil diupdate!", Toast.LENGTH_SHORT).show()
            }

            finish()
        }
    }


    private fun setupTimePicker(et: EditText) {
        et.inputType = 0
        et.isFocusable = false
        et.setOnClickListener {
            val cal = Calendar.getInstance()
            TimePickerDialog(
                this,
                { _, hour, minute ->
                    val hh = hour.toString().padStart(2, '0')
                    val mm = minute.toString().padStart(2, '0')
                    et.setText("$hh:$mm")
                },
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }
    }
}
