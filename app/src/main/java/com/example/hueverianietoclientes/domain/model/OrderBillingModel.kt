package com.example.hueverianietoclientes.domain.model

import com.google.firebase.Timestamp

data class OrderBillingModel(
    val orderId: Long?,
    val orderDatetime: Timestamp,
    val paymentMethod: Long,
    val totalPrice: Number?,
    val paid: Boolean
)
