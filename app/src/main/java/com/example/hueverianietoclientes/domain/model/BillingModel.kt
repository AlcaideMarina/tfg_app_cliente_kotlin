package com.example.hueverianietoclientes.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BillingModel (
    var paymentByCash: Number = 0,
    var paymentByReceipt: Number = 0,
    var paymentByTransfer: Number = 0,
    var paid: Number = 0,
    var toBePaid: Number = 0,
    var totalPrice: Number = 0,
    //var orderBillingModelList: List<OrderBillingModel>
) : Parcelable