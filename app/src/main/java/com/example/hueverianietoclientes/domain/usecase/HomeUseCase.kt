package com.example.hueverianietoclientes.domain.usecase

import com.example.hueverianietoclientes.data.network.AuthenticationService
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val authenticationService: AuthenticationService
)