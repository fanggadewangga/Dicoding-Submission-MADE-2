package com.fangga.githubuserapp.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fangga.core.domain.usecase.UserUseCase

class MainViewModel(private val userUseCase: UserUseCase): ViewModel(){

    fun searchUsers(query: String) = userUseCase.getAllUsers(query).asLiveData()
}