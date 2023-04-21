package com.example.hueverianietoclientes.data.network

sealed class LoginResponse {
    object Error : LoginResponse()
    object Success : LoginResponse()
}