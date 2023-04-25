package com.example.hueverianietoclientes.utils

import android.view.View
import com.example.hueverianietoclientes.data.network.OrderData
import com.example.hueverianietoclientes.data.network.DBOrderFieldData
import com.example.hueverianietoclientes.domain.model.GridTextItemModel
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
            data["order"] as Map<String, Map<String, Number>>,
            data["order_datetime"] as Timestamp,
            data["order_id"] as Long,
            data["paid"] as Boolean,
            data["payment_method"] as Long,
            data["status"] as Long,
            data["total_price"] as Long?
        )
    }

    // para conseguir timestamp: Timestamp(System.currentTimeMillis()),

    fun getOrderDataGridModel(orderData: OrderData) : List<GridTextItemModel> {

        val dbOrderModel = orderDataToBDOrderModel(orderData)

        return listOf(

            GridTextItemModel(
                true, "XL"
            ),
            GridTextItemModel(
                true, "Docena:"
            ),
            GridTextItemModel(
                false, dbOrderModel.xlDozenQuantity.toString() + "   uds.", false
            ),
            GridTextItemModel(
                true, (dbOrderModel.xlDozenPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemModel(
                true, "Caja:"
            ),
            GridTextItemModel(
                false, dbOrderModel.xlBoxQuantity.toString() + "   uds.", false
            ),
            GridTextItemModel(
                true, (dbOrderModel.xlBoxPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemModel(
                true, "L"
            ),
            GridTextItemModel(
                true, "Docena:"
            ),
            GridTextItemModel(
                false, dbOrderModel.lDozenQuantity.toString() + "   uds.", false
            ),
            GridTextItemModel(
                true, (dbOrderModel.lDozenPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemModel(
                true, "Caja:"
            ),
            GridTextItemModel(
                false, dbOrderModel.lBoxQuantity.toString() + "   uds.", false
            ),
            GridTextItemModel(
                true, (dbOrderModel.lBoxPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemModel(
                true, "M"
            ),
            GridTextItemModel(
                true, "Docena:"
            ),
            GridTextItemModel(
                false, dbOrderModel.mDozenQuantity.toString() + "   uds.", false
            ),
            GridTextItemModel(
                true, (dbOrderModel.mDozenPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemModel(
                true, "Caja:"
            ),
            GridTextItemModel(
                false, dbOrderModel.mBoxQuantity.toString() + "   uds.", false
            ),
            GridTextItemModel(
                true, (dbOrderModel.mBoxPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemModel(
                true, "S"
            ),
            GridTextItemModel(
                true, "Docena:"
            ),
            GridTextItemModel(
                false, dbOrderModel.sDozenQuantity.toString() + "   uds.", false
            ),
            GridTextItemModel(
                true, (dbOrderModel.sDozenPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemModel(
                true, "Caja:"
            ),
            GridTextItemModel(
                false, dbOrderModel.sBoxQuantity.toString() + "   uds.", false
            ),
            GridTextItemModel(
                true, (dbOrderModel.sBoxPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
        )
    }

    fun orderDataToBDOrderModel(orderData: OrderData) : DBOrderFieldData {

        val order = orderData.order
        val dbOrderFieldData = DBOrderFieldData()

        if (order.containsKey("xl_box")) {
            val xlBox = order["xl_box"]!!
            dbOrderFieldData.xlBoxPrice = xlBox["price"]
            dbOrderFieldData.xlBoxQuantity = xlBox["quantity"]?.toLong()
        }
        if (order.containsKey("xl_dozen")) {
            val xlDozen = order["xl_dozen"]!!
            dbOrderFieldData.xlDozenPrice = xlDozen["price"]
            dbOrderFieldData.xlDozenQuantity = xlDozen["quantity"]?.toLong()
        }

        if (order.containsKey("l_box")) {
            val lBox = order["l_box"]!!
            dbOrderFieldData.lBoxPrice = lBox["price"]
            dbOrderFieldData.lBoxQuantity = lBox["quantity"]?.toLong()
        }
        if (order.containsKey("l_dozen")) {
            val lDozen = order["l_dozen"]!!
            dbOrderFieldData.lDozenPrice = lDozen["price"]
            dbOrderFieldData.lDozenQuantity = lDozen["quantity"]?.toLong()
        }

        if (order.containsKey("m_box")) {
            val mBox = order["m_box"]!!
            dbOrderFieldData.mBoxPrice = mBox["price"]
            dbOrderFieldData.mBoxQuantity = mBox["quantity"]?.toLong()
        }
        if (order.containsKey("m_dozen")) {
            val mDozen = order["m_dozen"]!!
            dbOrderFieldData.mDozenPrice = mDozen["price"]
            dbOrderFieldData.mDozenQuantity = mDozen["quantity"]?.toLong()
        }

        if (order.containsKey("s_box")) {
            val sBox = order["s_box"]!!
            dbOrderFieldData.sBoxPrice = sBox["price"]
            dbOrderFieldData.sBoxQuantity = sBox["quantity"]?.toLong()
        }
        if (order.containsKey("s_dozen")) {
            val sDozen = order["s_dozen"]!!
            dbOrderFieldData.sDozenPrice = sDozen["price"]
            dbOrderFieldData.sDozenQuantity = sDozen["quantity"]?.toLong()
        }

        return dbOrderFieldData
    }

}