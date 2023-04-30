package com.example.hueverianietoclientes.domain.model

import com.google.firebase.Timestamp

data class BillingModel (
    var paymentByCash: Number = 0,
    var paymentByReceipt: Number = 0,
    var paymentByTransfer: Number = 0,
    var paid: Number = 0,
    var toBePaid: Number = 0,
    var totalPrice: Number = 0,
    var orderBillingModelList: List<OrderBillingModel>
)