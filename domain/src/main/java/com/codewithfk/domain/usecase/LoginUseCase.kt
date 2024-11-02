package com.codewithfk.domain.usecase

import com.codewithfk.domain.repository.UserRepository

class LoginUseCase(private val userRepository: UserRepository) {
    suspend fun execute(username: String, password: String) =
        userRepository.login(username, password)
}