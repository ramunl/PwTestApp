package ru.pwtest.pwapp.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.feature.sign_in.view.SignInActivity

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startAuthorization()
    }

    private fun startAuthorization() {
        startActivity(Intent(this, SignInActivity::class.java))
    }

}
