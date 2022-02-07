package com.example.vrades

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val SPLASH_TIME_OUT: Long = 1000
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)


            startActivity(intent)

        }, SPLASH_TIME_OUT)
    }
}
