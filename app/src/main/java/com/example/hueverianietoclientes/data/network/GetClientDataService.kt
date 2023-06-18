package com.example.hueverianietoclientes.data.network

import com.example.hueverianietoclientes.utils.ClientUtils
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetClientDataService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getClientData(uid: String): ClientData? = runCatching {
        firebaseClient.db
            .collection("client_info")
            .whereEqualTo("uid", uid)
            .get()
            .await()
    }.toClientData()

    private fun Result<QuerySnapshot>.toClientData() = when (val result = getOrNull()) {
        null -> null
        else -> {
            if (!result.isEmpty && result.documents.size > 0 && result.documents[0] != null
                && result.documents[0].data != null
            ) {
                ClientUtils.mapToParcelable(result.documents[0].data!!, result.documents[0].id)
            } else null
        }
    }

}
