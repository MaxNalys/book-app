package com.example.bookapp

import android.app.Application
import com.google.firebase.FirebaseApp

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Ініціалізуємо Firebase
        FirebaseApp.initializeApp(this)
    }
}