package com.example.hueverianietoclientes.utils

import com.example.hueverianietoclientes.data.network.OrderData
import com.google.firebase.Timestamp

object OrderUtils {

    fun parcelableToMap(orderData: OrderData) : MutableMap<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        map["approximate_delivery_datetime"] = orderData.approxDeliveryDatetime
        map["created_by"] = orderData.createdBy
        map["delivery_datetime"] = orderData.deliveryDatetime
        map["delivery_dni"] = orderData.deliveryDni
        map["delivery_note"] = orderData.deliveryNote
        map["delivery_person"] = orderData.deliveryPerson
        map["notes"] = orderData.notes
        map["order"] = orderData.order
        map["order_datetime"] = orderData.orderDatetime
        map["order_id"] = orderData.orderId
        map["paid"] = orderData.paid
        map["payment_method"] = orderData.paymentMethod
        map["status"] = orderData.status
        map["total_price"] = orderData.totalPrice
        return map
    }

    fun mapToParcelable(data: MutableMap<String, Any?>) : OrderData {
        return OrderData(
            data["approximate_delivery_datetime"] as Timestamp,
            data["created_by"] as String,
            data["delivery_datetime"] as Timestamp?,
            data["delivery_dni"] as String?,
            data["delivery_note"] as Long?,
            data["delivery_person"] as String?,
            data["notes"] as String?,
            data["order"] as Map<String, Map<String, Long>>,
            data["order_datetime"] as Timestamp?,
            data["order_id"] as Long,
            data["paid"] as Boolean,
            data["payment_method"] as Long,
            data["status"] as Long,
            data["total_price"] as Long?
        )
    }

    // para conseguir timestamp: Timestamp(System.currentTimeMillis()),

}