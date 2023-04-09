package com.fangga.githubuserapp.presentation.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import com.fangga.core.base.BaseActivity
import com.fangga.githubuserapp.databinding.ActivitySplashBinding
import com.fangga.githubuserapp.presentation.ui.main.MainActivity
import com.fangga.githubuserapp.utils.Constant.TIME_SPLASH
import com.fangga.core.utils.ScreenOrientation
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private val viewModel: SplashViewModel by viewModel()

    override fun inflateViewBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }

    override fun ActivitySplashBinding.binder() {
        val handler = Handler(mainLooper)

        handler.postDelayed({
            viewModel.getTheme().observe(this@SplashActivity) { isLightModeActive ->
                if (isLightModeActive) {
                    intentToMainActivity()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                } else {
                    intentToMainActivity()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }
        }, TIME_SPLASH)

        supportActionBar?.hide()
    }

    private fun intentToMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
}