package com.example.uts

import android.widget.Button

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class WelcomingPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcoming_page)

        val btnGetStarted = findViewById<Button>(R.id.btnGetStarted)
        btnGetStarted.setOnClickListener {
            // Pindahkan ke GetStartedActivity saat tombol "Mulai" atau "Get Started" ditekan
            startActivity(Intent(this, GetStartedActivity::class.java))
        }
    }
}
