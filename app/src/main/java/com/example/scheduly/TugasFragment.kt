package com.example.scheduly

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
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


    private fun loadTugas(container: LinearLayout) {
        container.removeAllViews()

        val semuaTugas = Storage.getTugas(requireContext())

        for (tugas in semuaTugas) {
            val parts = tugas.split("#")

            if (parts.size >= 4) {
                val itemView = LayoutInflater.from(requireContext())
                    .inflate(R.layout.item_tugas, container, false)

                itemView.findViewById<TextView>(R.id.tvJudulTugas).text = parts[0]
                itemView.findViewById<TextView>(R.id.tvMataKuliah).text = parts[1]
                itemView.findViewById<TextView>(R.id.tvDeskripsiTugas).text = parts[3]
                itemView.findViewById<TextView>(R.id.tvDeadline).text = parts[2]

                container.addView(itemView)
            }
        }
    }
}