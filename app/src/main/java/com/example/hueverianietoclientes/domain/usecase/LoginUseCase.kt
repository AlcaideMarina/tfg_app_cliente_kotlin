package com.example.hueverianietoclientes.domain.usecase

import com.example.hueverianietoclientes.data.network.AuthenticationService
import com.example.hueverianietoclientes.data.network.LoginResponse
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticationService: AuthenticationService
) {

    suspend operator fun invoke(email: String, password: String): LoginResponse =
        authenticationService.login(email, password)

}
