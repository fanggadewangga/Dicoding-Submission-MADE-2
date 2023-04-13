package com.fangga.githubuserapp.presentation.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.fangga.githubuserapp.databinding.ActivitySplashBinding
import com.fangga.githubuserapp.presentation.ui.main.MainActivity
import com.fangga.githubuserapp.utils.Constant.TIME_SPLASH
import org.koin.androidx.viewmodel.ext.android.viewModel


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val handler = Handler(mainLooper)
            viewModel.getTheme().observe(this@SplashActivity) { isLightModeActive ->
                if (isLightModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }
            handler.postDelayed({
                intentToMainActivity()
            }, TIME_SPLASH)

            supportActionBar?.hide()
        }
    }
    private fun intentToMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
}