package com.example.hueverianietoclientes.domain.model

import android.view.View.OnClickListener
import com.google.firebase.Timestamp

class OrderContainerModel(
    var orderDate: Timestamp,
    var orderId: Long,
    var orderSummary: String = "...",
    var price: Number,
    var status: Long,
    var deliveryDni: String?,
    var onClickListener: OnClickListener
)
