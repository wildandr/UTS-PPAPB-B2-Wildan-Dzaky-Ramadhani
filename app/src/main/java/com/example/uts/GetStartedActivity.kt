package com.example.uts

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

class GetStartedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)

        val edtNamaPengguna = findViewById<EditText>(R.id.edtNamaPengguna)
        val spinnerSatuanBerat = findViewById<Spinner>(R.id.spinnerSatuanBerat)
        val edtBeratSaatIni = findViewById<EditText>(R.id.edtBeratSaatIni)
        val edtBeratDiinginkan = findViewById<EditText>(R.id.edtBeratDiinginkan)
        val spinnerTujuanDiet = findViewById<Spinner>(R.id.spinnerTujuanDiet)
        val datePickerTarget = findViewById<DatePicker>(R.id.datePickerTarget)
        val edtTargetKalori = findViewById<EditText>(R.id.edtTargetKalori)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        // Adapter untuk Spinner Satuan Berat
        val satuanBeratAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.satuan_berat,
            android.R.layout.simple_spinner_item
        )
        satuanBeratAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSatuanBerat.adapter = satuanBeratAdapter

        // Adapter untuk Spinner Tujuan Diet
        val tujuanDietAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.tujuan_diet,
            android.R.layout.simple_spinner_item
        )
        tujuanDietAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTujuanDiet.adapter = tujuanDietAdapter

        btnSimpan.setOnClickListener {
            // Mengambil input dari pengguna
            val namaPengguna = edtNamaPengguna.text.toString()
            val satuanBerat = spinnerSatuanBerat.selectedItem.toString()
            val beratSaatIni = edtBeratSaatIni.text.toString().toDouble()
            val beratDiinginkan = edtBeratDiinginkan.text.toString().toDouble()
            val tujuanDiet = spinnerTujuanDiet.selectedItem.toString()
            val tanggalTarget = "${datePickerTarget.dayOfMonth}-${datePickerTarget.month + 1}-${datePickerTarget.year}"
            val targetKalori = edtTargetKalori.text.toString().toDouble()

            // Membuat objek User untuk menyimpan data
            val user = User(namaPengguna, beratSaatIni, beratDiinginkan, tujuanDiet, tanggalTarget, targetKalori)

            // Simpan data ke Shared Preferences
            val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("user", Gson().toJson(user))
            editor.apply()

            // Pindah ke Halaman Home
            startActivity(Intent(this, HomeActivity::class.java))
            finish() // Menutup GetStartedActivity
        }
    }
}
