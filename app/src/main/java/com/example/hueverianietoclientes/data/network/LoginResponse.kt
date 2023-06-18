package com.example.hueverianietoclientes.data.network

sealed class LoginResponse {
    object Error : LoginResponse()
    data class Success(val uid: String, val clientData: ClientData?) : LoginResponse()
}
