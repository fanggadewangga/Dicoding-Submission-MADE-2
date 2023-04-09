package com.fangga.core.data

import com.fangga.core.data.source.local.LocalDataSource
import com.fangga.core.data.source.remote.RemoteDataSource
import com.fangga.core.data.source.remote.network.ApiResponse
import com.fangga.core.data.source.remote.response.UserResponse
import com.fangga.core.domain.model.User
import com.fangga.core.domain.repository.IUserRepository
import com.fangga.core.utils.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): IUserRepository {

    override fun getAllUsers(query: String?): Flow<Resource<List<User>>> {
        return object : NetworkOnlyResource<List<User>, List<UserResponse>>() {
            override fun loadFromNetwork(data: List<UserResponse>): Flow<List<User>> {
                return Mapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> {
                return remoteDataSource.getAllUsers(query!!)
            }

        }.asFlow()
    }

    override fun getAllFollowers(username: String): Flow<Resource<List<User>>> {
        return object : NetworkOnlyResource<List<User>, List<UserResponse>>() {
            override fun loadFromNetwork(data: List<UserResponse>): Flow<List<User>> {
                return Mapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> {
                return remoteDataSource.getAllFollowers(username)
            }

        }.asFlow()
    }

    override fun getAllFollowing(username: String): Flow<Resource<List<User>>> {
        return object : NetworkOnlyResource<List<User>, List<UserResponse>>() {
            override fun loadFromNetwork(data: List<UserResponse>): Flow<List<User>> {
                return Mapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> {
                return remoteDataSource.getAllFollowing(username)
            }
        }.asFlow()
    }

    override fun getDetailUser(username: String): Flow<Resource<User>> {
        return object : NetworkOnlyResource<User, UserResponse>() {
            override fun loadFromNetwork(data: UserResponse): Flow<User> {
                return Mapper.mapResponseToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<UserResponse>> {
                return remoteDataSource.getUserDetail(username)
            }

        }.asFlow()
    }

    override fun getFavoriteUser(): Flow<List<User>> {
        return localDataSource.getFavoriteUser().map {
            Mapper.mapEntitiesToDomain(it)
        }
    }

    override fun getDetailState(username: String): Flow<User>? {
        return localDataSource.getFavoritedUserDetail(username)?.map {
            Mapper.mapEntityToDomain(it)
        }
    }

    override fun getTheme(): Flow<Boolean> = localDataSource.getTheme()

    override suspend fun insertUser(user: User) {
        val domainUser = Mapper.mapDomainToEntity(user)
        return localDataSource.insertFavoritedUser(domainUser)
    }

    override suspend fun deleteUser(user: User): Int {
        val domainUser = Mapper.mapDomainToEntity(user)
        return localDataSource.deleteUser(domainUser)
    }

    override suspend fun saveTheme(isLightModeActive: Boolean) = localDataSource.saveTheme(isLightModeActive)
}