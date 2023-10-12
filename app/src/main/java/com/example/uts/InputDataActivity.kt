package com.example.uts

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.util.Calendar

class InputDataActivity : AppCompatActivity() {

    private lateinit var edtWaktuMakan: EditText
    private lateinit var edtWaktuWorkout: EditText

    private var waktuMakan: String? = null
    private var waktuWorkout: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_data)

        val edtNamaMakanan = findViewById<EditText>(R.id.edtNamaMakanan)
        edtWaktuMakan = findViewById(R.id.edtWaktuMakan)
        val spinnerJenisKaloriIn = findViewById<Spinner>(R.id.spinnerJenisKaloriIn)
        val edtJumlahKaloriIn = findViewById<EditText>(R.id.edtJumlahKaloriIn)
        val edtNamaWorkout = findViewById<EditText>(R.id.edtNamaWorkout)
        edtWaktuWorkout = findViewById(R.id.edtWaktuWorkout)
        val edtDurasiWorkout = findViewById<EditText>(R.id.edtDurasiWorkout)
        val edtKaloriTerbakar = findViewById<EditText>(R.id.edtKaloriTerbakar)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        val jenisKaloriInAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.jenis_kalori_in,
            android.R.layout.simple_spinner_item
        )
        jenisKaloriInAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerJenisKaloriIn.adapter = jenisKaloriInAdapter

        edtWaktuMakan.setOnClickListener { showTimePickerDialog(true) }
        edtWaktuWorkout.setOnClickListener { showTimePickerDialog(false) }

        btnSimpan.setOnClickListener {
            val namaMakanan = edtNamaMakanan.text.toString()
            val waktuMakan = this.waktuMakan ?: return@setOnClickListener
            val jenisKaloriIn = spinnerJenisKaloriIn.selectedItem.toString()
            val jumlahKaloriIn = edtJumlahKaloriIn.text.toString().toDouble()
            val namaWorkout = edtNamaWorkout.text.toString()
            val waktuWorkout = this.waktuWorkout ?: return@setOnClickListener
            val durasiWorkout = edtDurasiWorkout.text.toString().toDouble()
            val kaloriTerbakar = edtKaloriTerbakar.text.toString().toDouble()

            val calorieIn = CalorieIn(namaMakanan, waktuMakan, jenisKaloriIn, jumlahKaloriIn)
            val calorieOut = CalorieOut(namaWorkout, waktuWorkout, durasiWorkout, kaloriTerbakar)

            // Simpan data ke Shared Preferences
            val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("calorieIn", Gson().toJson(calorieIn))
            editor.putString("calorieOut", Gson().toJson(calorieOut))
            editor.apply()

            // Kembali ke HomeActivity
            startActivity(Intent(this, HomeActivity::class.java))
            finish() // Menutup InputDataActivity
        }
    }

    private fun showTimePickerDialog(isWaktuMakan: Boolean) {
        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                val time = "$hourOfDay:$minute"
                if (isWaktuMakan) {
                    waktuMakan = time
                    edtWaktuMakan.setText(time)
                } else {
                    waktuWorkout = time
                    edtWaktuWorkout.setText(time)
                }
            },
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE),
            true
        )
        timePickerDialog.show()
    }
}
