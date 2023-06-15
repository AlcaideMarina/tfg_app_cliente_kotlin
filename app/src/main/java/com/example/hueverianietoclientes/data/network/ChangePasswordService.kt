package com.example.hueverianietoclientes.data.network

import com.google.firebase.auth.EmailAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class ChangePasswordService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun changePassword(oldPass: String, newPass: String) = runCatching {
            val email: String = firebaseClient.auth.currentUser!!.email!!
            val credential = EmailAuthProvider.getCredential(email, oldPass)
            firebaseClient.auth.currentUser!!
                .reauthenticate(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        firebaseClient.auth.currentUser!!
                            .updatePassword(newPass)
                    }
                }
                .await()

    }

}
