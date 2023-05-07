package com.example.hueverianietoclientes.data.network

import com.example.hueverianietoclientes.utils.OrderUtils
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NewOrderService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun addNewOrder(clientDocumentId: String, orderData: OrderData) : Boolean = runCatching {
        firebaseClient.db
            .collection("client_info")
            .document(clientDocumentId)
            .collection("orders")
            .add(
                OrderUtils.parcelableToMap(orderData)
            ).await()
    }.toOrderResult(orderData)

    private fun Result<DocumentReference>.toOrderResult(orderData: OrderData) = when (val result = getOrNull()) {
        null -> false
        else -> true
    }

}