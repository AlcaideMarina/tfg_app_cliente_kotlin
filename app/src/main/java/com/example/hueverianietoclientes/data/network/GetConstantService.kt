package com.example.hueverianietoclientes.data.network

import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetConstantService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getConstant(constantName: String): Result<QuerySnapshot> = runCatching {
        firebaseClient.db
            .collection("default_constants")
            .whereEqualTo("constant_name", constantName)
            .get()
            .await()
    }

}
