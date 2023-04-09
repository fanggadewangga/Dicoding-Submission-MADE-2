package com.fangga.githubuserapp.presentation.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fangga.core.domain.usecase.UserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingViewModel(private val userUseCase: UserUseCase): ViewModel() {
    suspend fun saveTheme(isLightModeActive: Boolean) = viewModelScope.launch {
        userUseCase.saveTheme(isLightModeActive)
    }

    fun getTheme() = userUseCase.getTheme().asLiveData(Dispatchers.IO)
}