package com.fangga.favorite.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fangga.core.domain.usecase.UserUseCase

class FavoriteViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    fun getFavorites() = userUseCase.getFavoriteUser().asLiveData()
}