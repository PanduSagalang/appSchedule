package com.example.scheduly

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class TugasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tugas, container, false)

        val containerList = view.findViewById<LinearLayout>(R.id.containerListTugas)
        val btnTambahTugasFragment = view.findViewById<Button>(R.id.btnTambahTugas)

        btnTambahTugasFragment.setOnClickListener {
            startActivity(Intent(requireContext(), TambahTugasActivity::class.java))
        }

        loadTugas(containerList)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadTugas(view.findViewById(R.id.containerListTugas))
    }

    override fun onResume() {
        super.onResume()
        view?.findViewById<LinearLayout>(R.id.containerListTugas)?.let {
            loadTugas(it)
        }
    }


    private fun loadTugas(container: LinearLayout) {
        container.removeAllViews()

        val semuaTugas = Storage.getTugas(requireContext())

        for (tugas in semuaTugas) {
            val parts = tugas.split("#")

            if (parts.size >= 5) {
                val itemView = LayoutInflater.from(requireContext())
                    .inflate(R.layout.item_tugas, container, false)

                val namaTugas = parts[1]
                val matkul = parts[2]
                val hari = parts[3]
                val catatan = parts[4]

                itemView.findViewById<TextView>(R.id.tvJudulTugas).text = namaTugas
                itemView.findViewById<TextView>(R.id.tvMataKuliah).text = matkul
                itemView.findViewById<TextView>(R.id.tvDeadline).text = hari
                itemView.findViewById<TextView>(R.id.tvDeskripsiTugas).text = catatan

                itemView.setOnClickListener {
                    val intent = Intent(requireContext(), TambahTugasActivity::class.java)
                    intent.putExtra("editData", tugas)
                    startActivity(intent)
                }

                val btnHapus = itemView.findViewById<Button>(R.id.btnHapusTugas)
                btnHapus.setOnClickListener {
                    Storage.deleteTugas(requireContext(), tugas) // tetap pakai string full
                    loadTugas(container)
                    Toast.makeText(requireContext(), "Tugas dihapus!", Toast.LENGTH_SHORT).show()
                }

                container.addView(itemView)
            }
        }
    }
}