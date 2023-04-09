package com.fangga.githubuserapp.presentation.ui.following

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fangga.core.domain.usecase.UserUseCase

class FollowingViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    fun getUserFollowing(username: String) = userUseCase.getAllFollowing(username).asLiveData()
}