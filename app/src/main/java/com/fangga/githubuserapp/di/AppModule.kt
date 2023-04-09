package com.fangga.githubuserapp.di

import com.fangga.core.domain.usecase.UserInteractor
import com.fangga.core.domain.usecase.UserUseCase
import com.fangga.githubuserapp.presentation.ui.detail.DetailViewModel
import com.fangga.githubuserapp.presentation.ui.follower.FollowerViewModel
import com.fangga.githubuserapp.presentation.ui.following.FollowingViewModel
import com.fangga.githubuserapp.presentation.ui.main.MainViewModel
import com.fangga.githubuserapp.presentation.ui.setting.SettingViewModel
import com.fangga.githubuserapp.presentation.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<UserUseCase> { UserInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { FollowerViewModel(get()) }
    viewModel { FollowingViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { SettingViewModel(get()) }
    viewModel { SplashViewModel(get()) }
}