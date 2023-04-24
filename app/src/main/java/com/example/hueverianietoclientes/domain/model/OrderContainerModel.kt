package com.example.hueverianietoclientes.domain.model

import android.view.View.OnClickListener
import com.google.firebase.Timestamp

class OrderContainerModel(
    var orderDate: Timestamp,
    var orderId: Long,
    var company: String,
    //var orderSummary: String,
    var price: Long,
    var status: Long,
    var onClickListener: OnClickListener
)
