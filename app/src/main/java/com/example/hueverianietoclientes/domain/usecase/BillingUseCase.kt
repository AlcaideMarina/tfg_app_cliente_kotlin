package com.example.hueverianietoclientes.domain.usecase

import com.example.hueverianietoclientes.data.network.BillingService
import com.example.hueverianietoclientes.data.network.OrderData
import javax.inject.Inject

class BillingUseCase @Inject constructor(
    private val billingService: BillingService
) {

    suspend operator fun invoke(documentId: String) : List<OrderData?>? =
        billingService.getMonthlyOrders(documentId)

}