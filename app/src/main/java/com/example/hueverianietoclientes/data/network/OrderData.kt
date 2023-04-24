package com.example.hueverianietoclientes.data.network

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderData (
    val approxDeliveryDatetime: Timestamp,
    val createdBy: String,
    val deliveryDatetime: Timestamp?,
    val deliveryDni: String?,
    val deliveryNote: Long?,
    val deliveryPerson: String?,
    val notes: String?,
    val order: Map<String, Map<String, Long>>,
    val orderDatetime: Timestamp,
    val orderId: Long,
    val paid: Boolean,
    val paymentMethod: Long,
    val status: Long,
    val totalPrice: Long?
) : Parcelable
