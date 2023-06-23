package com.example.hueverianietoclientes.data.network

import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetOrderIdService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getOrderId(documentId: String): Long? = runCatching {
        firebaseClient.db
            .collection("client_info")
            .document(documentId)
            .collection("orders")
            .get()
            .await()
    }.parseToInt()

    private fun Result<QuerySnapshot>.parseToInt() = when (val result = getOrNull()) {
        null -> null
        else -> (result.documents.size).toLong()
    }

}
