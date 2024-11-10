    package com.example.bookapp.viewmodel

    import android.content.Context
    import android.content.Intent
    import com.example.bookapp.ui.MainActivity
    import com.example.bookapp.ui.SignInActivity
    import com.example.bookapp.ui.SplashActivity

    class SplashScreenFactory(private val context: Context) {

        // Фабричний метод для створення SplashActivity
        fun createSplashActivity(): Intent {
            return Intent(context, SignInActivity::class.java)
        }

        // Метод для переходу на MainActivity після SplashScreen
        fun navigateToMain() {
            val mainIntent = Intent(context, SignInActivity::class.java)
            context.startActivity(mainIntent)
        }
    }
