package com.fangga.core.domain.usecase

import com.fangga.core.data.Resource
import com.fangga.core.domain.model.User
import com.fangga.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class UserInteractor(private val userRepository: IUserRepository): UserUseCase {
    override fun getAllUsers(query: String?): Flow<Resource<List<User>>> = userRepository.getAllUsers(query)
    override fun getAllFollowers(username: String): Flow<Resource<List<User>>> = userRepository.getAllFollowers(username)
    override fun getAllFollowing(username: String): Flow<Resource<List<User>>> = userRepository.getAllFollowing(username)
    override fun getDetailUser(username: String): Flow<Resource<User>> = userRepository.getDetailUser(username)
    override fun getFavoriteUser(): Flow<List<User>> = userRepository.getFavoriteUser()
    override fun getDetailState(username: String): Flow<User>? = userRepository.getDetailState(username)
    override fun getTheme(): Flow<Boolean> = userRepository.getTheme()
    override suspend fun insertUser(user: User) = userRepository.insertUser(user)
    override suspend fun deleteUser(user: User): Int = userRepository.deleteUser(user)
    override suspend fun saveTheme(isLightModeActive: Boolean) = userRepository.saveTheme(isLightModeActive)
}