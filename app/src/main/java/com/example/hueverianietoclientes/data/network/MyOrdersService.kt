package com.example.hueverianietoclientes.data.network

import javax.inject.Inject

class MyOrdersService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getOrders() {

    }

}