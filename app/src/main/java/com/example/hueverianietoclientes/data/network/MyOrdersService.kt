package com.example.hueverianietoclientes.data.network

import com.example.hueverianietoclientes.utils.OrderUtils
import com.google.firebase.firestore.QuerySnapshot
import com.google.firestore.v1.StructuredQuery.Order
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MyOrdersService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getOrders(documentId : String) = runCatching {
        firebaseClient.db
            .collection("client_info")
            .document(documentId)
            .collection("orders")
            .get()
            .await()
    }.toOrderDataList()

    private fun Result<QuerySnapshot>.toOrderDataList() : List<OrderData?>? = when(val result = getOrNull()) {
        null -> null
        else -> {
            val list = mutableListOf<OrderData>()
            if (!result.isEmpty && result.documents.size > 0) {
                for (item in result.documents) {
                    if (item.data != null) {
                        val data = item.data!!
                        data["order"]
                        list.add(OrderUtils.mapToParcelable(data))
                    }
                }
            }
            list
        }
    }

}