package com.example.scheduly

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TugasFragment : Fragment() {

    private lateinit var rvTugas: RecyclerView
    private lateinit var adapter: TugasAdapter
    private lateinit var tambahTugasLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tambahTugasLauncher =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    loadTugas()
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_tugas, container, false)

        rvTugas = view.findViewById(R.id.rvTugas)
        val btnTambah = view.findViewById<Button>(R.id.btnTambahTugas)
        val tvTanggal = view.findViewById<TextView>(R.id.txtTanggalHariIni)

        tvTanggal.text = SimpleDateFormat(
            "EEEE, dd MMMM yyyy",
            Locale("id", "ID")
        ).format(Date())

        rvTugas.layoutManager = LinearLayoutManager(requireContext())

        adapter = TugasAdapter(
            mutableListOf(),
            onDelete = {
                Storage.deleteTugasObj(requireContext(), it)
                loadTugas()
            },
            onEdit = { tugas ->
                tambahTugasLauncher.launch(
                    Intent(requireContext(), TambahTugasActivity::class.java)
                        .putExtra("tugasId", tugas.id)
                )
            }
        )

        rvTugas.adapter = adapter

        btnTambah.setOnClickListener {
            tambahTugasLauncher.launch(
                Intent(requireContext(), TambahTugasActivity::class.java)
            )
        }

        loadTugas()
        return view
    }

    private fun loadTugas() {
        if (!isAdded) return
        val list = Storage.getTugasList(requireContext())
        adapter.submitData(list)
    }
}
