package com.example.hueverianietoclientes.domain.usecase

import com.example.hueverianietoclientes.data.network.ChangePasswordService
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val changePasswordService: ChangePasswordService
) {

    suspend operator fun invoke(oldPass: String, newPass: String) : Boolean {
        val result = changePasswordService.changePassword(oldPass, newPass)
        return result.isSuccess
    }

}
