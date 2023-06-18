package com.example.hueverianietoclientes.data.network

data class ClientLoginData(
    val email: String = "",
    val password: String = "",
    val error: Boolean = false
)
