package com.fangga.githubuserapp.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fangga.core.domain.model.User
import com.fangga.core.domain.usecase.UserUseCase
import kotlinx.coroutines.launch

class DetailViewModel(private val userUseCase: UserUseCase): ViewModel() {

    fun getUserDetail(username: String) = userUseCase.getDetailUser(username).asLiveData()

    fun getDetailState(username: String) = userUseCase.getDetailState(username)?.asLiveData()

    fun insertFavoritedUser(user: User) {
        viewModelScope.launch {
            userUseCase.insertUser(user)
        }
    }

    fun deleteFavoritedUser(user: User) = viewModelScope.launch {
        userUseCase.deleteUser(user)
    }
}