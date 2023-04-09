package com.fangga.githubuserapp.presentation.ui.follower

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fangga.core.domain.usecase.UserUseCase

class FollowerViewModel(private val userUseCase: UserUseCase): ViewModel() {
    fun getUserFollowers(username: String) = userUseCase.getAllFollowers(username).asLiveData()
}