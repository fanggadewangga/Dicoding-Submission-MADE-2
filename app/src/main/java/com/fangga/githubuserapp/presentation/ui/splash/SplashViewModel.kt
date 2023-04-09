package com.fangga.githubuserapp.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fangga.core.domain.usecase.UserUseCase
import kotlinx.coroutines.Dispatchers

class SplashViewModel(private val userUseCase: UserUseCase): ViewModel() {

    fun getTheme() = userUseCase.getTheme().asLiveData(Dispatchers.IO)
}