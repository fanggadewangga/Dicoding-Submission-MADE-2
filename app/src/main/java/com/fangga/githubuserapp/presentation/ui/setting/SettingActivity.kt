package com.fangga.githubuserapp.presentation.ui.setting

import androidx.appcompat.app.AppCompatDelegate
import com.fangga.core.base.BaseActivity
import com.fangga.githubuserapp.databinding.ActivitySettingBinding
import com.fangga.core.utils.ScreenOrientation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("DEPRECATION")
class SettingActivity : BaseActivity<ActivitySettingBinding>() {
    private val viewModel: SettingViewModel by viewModel()

    override fun inflateViewBinding(): ActivitySettingBinding {
        return ActivitySettingBinding.inflate(layoutInflater)
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }

    override fun ActivitySettingBinding.binder() {
        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.saveTheme(isChecked)
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getTheme().observe(this@SettingActivity) { isLightModeActive ->
                if (isLightModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchTheme.isChecked = false
                }
            }
        }

        supportActionBar?.hide()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}