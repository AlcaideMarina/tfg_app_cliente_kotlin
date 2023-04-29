package com.example.hueverianietoclientes.domain.usecase

import com.example.hueverianietoclientes.data.network.GetOrderIdService
import javax.inject.Inject

class GetOrderIdUseCase @Inject constructor(
    private val getOrderIdService : GetOrderIdService
) {

    suspend operator fun invoke(documentId: String) : Long? =
        getOrderIdService.getOrderId(documentId)

}
