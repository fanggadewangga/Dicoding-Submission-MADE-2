package com.fangga.core.utils

import com.fangga.core.data.source.local.entity.UserEntity
import com.fangga.core.data.source.remote.response.UserResponse
import com.fangga.core.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object Mapper {
    fun mapResponsesToDomain(response: List<UserResponse>): Flow<List<User>> {
        val listOfUser = ArrayList<User>()
        response.map {
            val user = User(
                it.id,
                it.username,
                it.name,
                it.location,
                it.company,
                it.repository,
                it.follower,
                it.following,
                it.avatar,
                false
            )
            listOfUser.add(user)
        }
        return flowOf(listOfUser)
    }

    fun mapResponseToDomain(response: UserResponse): Flow<User> {
        return flowOf(
            User(
                response.id,
                response.username,
                response.name,
                response.location,
                response.company,
                response.repository,
                response.follower,
                response.following,
                response.avatar,
                false
            )
        )
    }

    fun mapEntitiesToDomain(entities: List<UserEntity>): List<User> =
        entities.map { userEntity ->
            User(
                userEntity.id,
                userEntity.username,
                userEntity.name,
                userEntity.location,
                userEntity.company,
                userEntity.repository,
                userEntity.follower,
                userEntity.following,
                userEntity.avatar,
                false
            )
        }

    fun mapEntityToDomain(entity: UserEntity?): User {
        return User(
            entity?.id,
            entity?.username,
            entity?.name,
            entity?.location,
            entity?.company,
            entity?.repository,
            entity?.follower,
            entity?.following,
            entity?.avatar,
            entity?.isFavorite
        )
    }


    fun mapDomainToEntity(domain: User) = UserEntity(
        domain.id,
        domain.username,
        domain.name,
        domain.location,
        domain.company,
        domain.repository,
        domain.follower,
        domain.following,
        domain.avatar,
        domain.isFavorite
    )
}