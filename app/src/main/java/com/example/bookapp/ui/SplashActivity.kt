package com.example.bookapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.bookapp.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            // Завжди переходимо до SignInActivity
            startActivity(Intent(this, SignInActivity::class.java))
            finish() // Закриваємо SplashActivity
        }, 2000) // Затримка 2 секунди
    }
}
