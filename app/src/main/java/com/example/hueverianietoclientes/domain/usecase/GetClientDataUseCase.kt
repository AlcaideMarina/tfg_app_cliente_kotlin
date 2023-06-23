package com.example.hueverianietoclientes.domain.usecase

import com.example.hueverianietoclientes.data.network.ClientData
import com.example.hueverianietoclientes.data.network.GetClientDataService
import javax.inject.Inject

class GetClientDataUseCase @Inject constructor(
    private val getClientDataService: GetClientDataService
) {

    suspend operator fun invoke(uid: String): ClientData? =
        getClientDataService.getClientData(uid)

}
