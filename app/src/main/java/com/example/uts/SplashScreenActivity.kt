package com.example.uts

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Mengatur durasi Splash Screen selama 3 detik (3000 milidetik)
        Handler(Looper.getMainLooper()).postDelayed({
            // Setelah 3 detik, pindahkan ke WelcomingPageActivity
            startActivity(Intent(this, WelcomingPageActivity::class.java))
            finish()
        }, 3000)
    }
}

