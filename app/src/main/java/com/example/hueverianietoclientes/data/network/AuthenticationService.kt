package com.example.hueverianietoclientes.data.network

import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationService @Inject constructor(private val firebaseClient: FirebaseClient) {

    suspend fun login(email: String, password: String): LoginResponse = runCatching {
        firebaseClient.auth.signInWithEmailAndPassword(email, password).await()
    }.toLoginResult()

    private fun Result<AuthResult>.toLoginResult() = when (val result = getOrNull()) {
        null -> LoginResponse.Error
        else -> {
            val userId = result.user
            checkNotNull(userId)
            LoginResponse.Success(userId.uid, null)
        }
    }

}
