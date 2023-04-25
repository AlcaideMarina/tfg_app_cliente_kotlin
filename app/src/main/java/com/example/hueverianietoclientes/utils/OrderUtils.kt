package com.example.hueverianietoclientes.utils

import com.example.hueverianietoclientes.data.network.OrderData
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
            data["order"] as Map<String, Map<String, Long>>,
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

        // TODO: Settear valores que vienen del orderData - ¿hacemos un modelo para esto?
        // TODO: Si queremos ocultar, hay que ver una forma de pasar los valores para los que hay que cambiar el ancho - ¿se puede hacer en el adapter?


        /*if (orderData.order.containsKey("xl")) {
            list.add(GridTextItemModel(
                true, "XL"
            ))
            list.add(GridTextItemModel(
                false, orderData.order["xl"]?.get("quantity")?.toString(), false
            ))
            list.add(GridTextItemModel(
                true, orderData.order["xl"]?.get("price")?.toString() + " €/ud"
            ))
        }

        if (orderData.order.containsKey("l")) {
            list.add(GridTextItemModel(
                true, "L"
            ))
            list.add(GridTextItemModel(
                false, orderData.order["l"]?.get("quantity")?.toString(), false
            ))
            list.add(GridTextItemModel(
                true, "€/ud"
            ))
        }*/

        return listOf(

            GridTextItemModel(
                true, "XL"
            ),

            GridTextItemModel(
                true, "Docena:"
            ),
            GridTextItemModel(
                false, null, false
            ),
            GridTextItemModel(
                true, "€/ud"
            ),

            GridTextItemModel(
                true, "Caja:"
            ),
            GridTextItemModel(
                false, null, false
            ),
            GridTextItemModel(
                true, "€/ud"
            ),



            GridTextItemModel(
                true, "L"
            ),

            GridTextItemModel(
                true, "Docena:"
            ),
            GridTextItemModel(
                false, null, false
            ),
            GridTextItemModel(
                true, "€/ud"
            ),

            GridTextItemModel(
                true, "Caja:"
            ),
            GridTextItemModel(
                false, null, false
            ),
            GridTextItemModel(
                true, "€/ud"
            ),


            GridTextItemModel(
                true, "M"
            ),

            GridTextItemModel(
                true, "Docena:"
            ),
            GridTextItemModel(
                false, null, false
            ),
            GridTextItemModel(
                true, "€/ud"
            ),

            GridTextItemModel(
                true, "Caja:"
            ),
            GridTextItemModel(
                false, null, false
            ),
            GridTextItemModel(
                true, "€/ud"
            ),



            GridTextItemModel(
                true, "S"
            ),

            GridTextItemModel(
                true, "Docena:"
            ),
            GridTextItemModel(
                false, null, false
            ),
            GridTextItemModel(
                true, "€/ud"
            ),

            GridTextItemModel(
                true, "Caja:"
            ),
            GridTextItemModel(
                false, null, false
            ),
            GridTextItemModel(
                true, "€/ud"
            ),
        )
    }

}