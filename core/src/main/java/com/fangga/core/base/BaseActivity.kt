package com.fangga.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.fangga.core.utils.ScreenOrientation

abstract class BaseActivity<VB: ViewBinding>: AppCompatActivity() {
    private lateinit var _binding: VB
    val binding get() = _binding

    abstract fun inflateViewBinding(): VB
    abstract fun determineScreenOrientation(): ScreenOrientation?
    abstract fun VB.binder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflateViewBinding()
        setContentView(binding.root)

        val screenOrientation = determineScreenOrientation()

        requestedOrientation = if(screenOrientation != null) {
            if (screenOrientation == ScreenOrientation.PORTRAIT)
                android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            else
                android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        binding.apply {
            binder()
        }

    }
}