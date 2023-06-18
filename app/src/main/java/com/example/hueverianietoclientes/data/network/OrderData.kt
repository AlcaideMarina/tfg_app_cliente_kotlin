package com.example.hueverianietoclientes.data.network

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderData(
    val approxDeliveryDatetime: Timestamp,
    val clientId: Long,
    val company: String,
    val createdBy: String,
    val deliveryDatetime: Timestamp?,
    val deliveryDni: String?,
    val deliveryNote: Long?,
    val deliveryPerson: String?,
    val lot: String?,
    val notes: String?,
    var order: Map<String, Map<String, Number?>>,
    val orderDatetime: Timestamp,
    var orderId: Long?,
    val paid: Boolean,
    val paymentMethod: Long,
    val status: Long,
    val totalPrice: Number?,
    val documentId: String?
) : Parcelable

