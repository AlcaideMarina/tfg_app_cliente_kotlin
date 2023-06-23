package com.example.hueverianietoclientes.domain.usecase

import com.example.hueverianietoclientes.data.network.MyOrdersService
import com.example.hueverianietoclientes.data.network.OrderData
import javax.inject.Inject

class MyOrdersUseCase @Inject constructor(
    private val myOrdersService: MyOrdersService
) {

    suspend operator fun invoke(documentId: String): List<OrderData?>? =
        myOrdersService.getOrders(documentId)

}
