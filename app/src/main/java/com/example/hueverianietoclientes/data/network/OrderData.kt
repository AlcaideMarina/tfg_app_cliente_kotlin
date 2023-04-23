package com.example.hueverianietoclientes.data.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp

@Parcelize
data class OrderData (
    val aproxDeliveryDateTime: Timestamp,
    val createdBy: String,
    val deliveryDatetime: Timestamp?,
    val deliveryDni: String?,
    val deliveryNote: Long?,
    val deliveryPerson: String?,
    val notes: String?,
    val order: Map<String, Map<String, Long>>,
    val orderDatetime: Timestamp?,
    val oderId: Long,
    val paid: Boolean,
    val paymentMethod: Long,
    val status: Long,
    val totalPrice: Long?
) : Parcelable
