package com.example.uts

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.gson.Gson

import java.util.Calendar

class HomeActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)

        // Deklarasi tvRangkumanDataBaru
        val tvRangkumanDataBaru = findViewById<TextView>(R.id.tvRangkumanDataBaru)
        val tvTanggalHariIni = findViewById<TextView>(R.id.tvTanggalHariIni)
        val tvSisaKalori = findViewById<TextView>(R.id.tvSisaKalori)
        val tvKaloriInTerakhir = findViewById<TextView>(R.id.tvKaloriInTerakhir)
        val tvKaloriOutTerakhir = findViewById<TextView>(R.id.tvKaloriOutTerakhir)
        val btnInputData = findViewById<Button>(R.id.btnInputData)

        val userJson = sharedPreferences.getString("user", "")
        val user = Gson().fromJson(userJson, User::class.java)

        tvRangkumanDataBaru.text = "Hi ${user.nama}\nBerat badanmu saat ini: ${user.beratSaatIni}\nBerat yang kamu inginkan: ${user.beratYgDiinginkan}\nTujuan diet: ${user.tujuanDiet}\nTanggal target: ${user.tanggalTarget}\nTarget kalori harian: ${user.kaloriHarian}"
        tvRangkumanDataBaru.textSize = 18f
        tvRangkumanDataBaru.setPadding(tvRangkumanDataBaru.paddingLeft, tvRangkumanDataBaru.paddingTop, tvRangkumanDataBaru.paddingRight, 8)

        // Ambil tanggal hari ini
        val calendar = Calendar.getInstance()
        val tanggal = calendar.get(Calendar.DAY_OF_MONTH)
        val bulan = calendar.get(Calendar.MONTH) + 1 // Bulan dihitung dari 0-11
        val tahun = calendar.get(Calendar.YEAR)
        val tanggalHariIni = "$tanggal-$bulan-$tahun"
        tvTanggalHariIni.text = "Tanggal Hari Ini: $tanggalHariIni"

        // Ambil data kalori terakhir
        val calorieInJson = sharedPreferences.getString("calorieIn", "")
        val calorieIn = Gson().fromJson(calorieInJson, CalorieIn::class.java)

        val calorieOutJson = sharedPreferences.getString("calorieOut", "")
        val calorieOut = Gson().fromJson(calorieOutJson, CalorieOut::class.java)

        val sisaKalori = if (calorieIn != null && calorieOut != null) {
            user.kaloriHarian - (calorieIn.jumlahKalori - calorieOut.kaloriTerbakar)
        } else {
            user.kaloriHarian
        }

        tvSisaKalori.text = "Sisa Kalori: $sisaKalori"
        tvKaloriInTerakhir.text = "Kalori masuk terakhir: ${calorieIn?.jumlahKalori ?: 0.0}"
        tvKaloriOutTerakhir.text = "Kalori keluar terakhir: ${calorieOut?.kaloriTerbakar ?: 0.0}"

        btnInputData.setOnClickListener {
            startActivity(Intent(this, InputDataActivity::class.java))
            finish() // Menutup HomeActivity
        }

        setupChart()
    }

    private fun setupChart() {
        val pieChart = findViewById<PieChart>(R.id.pieChart)

        val calorieEntries = mutableListOf<PieEntry>()
        calorieEntries.add(PieEntry(1200f, "Kalori masuk"))
        calorieEntries.add(PieEntry(400f, "Kalori keluar"))

        val pieDataSet = PieDataSet(calorieEntries, "")
        pieDataSet.colors = listOf(Color.parseColor("#FF9E7E"), Color.parseColor("#30B5B3"))
        val pieData = PieData(pieDataSet)

        pieChart.data = pieData

        pieChart.description.isEnabled = false
        pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        pieChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        pieChart.legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        pieChart.legend.setDrawInside(false)

        pieChart.animateY(1000)

        pieChart.invalidate()
    }
}
