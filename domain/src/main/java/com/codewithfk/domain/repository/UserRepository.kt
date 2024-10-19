package com.codewithfk.domain.repository

import com.codewithfk.domain.model.UserDomainModel
import com.codewithfk.domain.network.ResultWrapper

interface UserRepository {
    suspend fun login(email: String, password: String): ResultWrapper<UserDomainModel>
    suspend fun register(
        email: String,
        password: String,
        name: String
    ): ResultWrapper<UserDomainModel>
}