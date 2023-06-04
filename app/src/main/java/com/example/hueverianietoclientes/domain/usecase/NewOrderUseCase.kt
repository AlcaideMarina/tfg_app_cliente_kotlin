package com.example.hueverianietoclientes.domain.usecase

import com.example.hueverianietoclientes.data.network.ClientData
import com.example.hueverianietoclientes.data.network.NewOrderService
import com.example.hueverianietoclientes.data.network.OrderData
import javax.inject.Inject

class NewOrderUseCase @Inject constructor(
    private val newOrderService: NewOrderService
) {

    suspend operator fun invoke(clientDocumentId: String, orderData: OrderData) : Boolean =
        newOrderService.addNewOrder(clientDocumentId, orderData)

}