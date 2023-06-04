package com.example.hueverianietoclientes.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianietoclientes.data.network.OrderData
import com.example.hueverianietoclientes.data.network.DBOrderFieldData
import com.example.hueverianietoclientes.data.network.EggPricesData
import com.example.hueverianietoclientes.domain.model.GridTextItemModel
import com.example.hueverianietoclientes.ui.components.hngridview.HNGridTextAdapter
import com.google.firebase.Timestamp
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.roundToInt

object OrderUtils {

    fun parcelableToMap(orderData: OrderData) : MutableMap<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        map["approximate_delivery_datetime"] = orderData.approxDeliveryDatetime
        map["client_id"] = orderData.clientId
        map["company"] = orderData.company
        map["created_by"] = orderData.createdBy
        map["delivery_datetime"] = orderData.deliveryDatetime
        map["delivery_dni"] = orderData.deliveryDni
        map["delivery_note"] = orderData.deliveryNote
        map["delivery_person"] = orderData.deliveryPerson
        map["lot"] = orderData.lot
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

    fun mapToParcelable(data: MutableMap<String, Any?>, documentId: String) : OrderData {
        return OrderData(
            data["approximate_delivery_datetime"] as Timestamp,
            data["client_id"] as Long,
            data["company"] as String,
            data["created_by"] as String,
            data["delivery_datetime"] as Timestamp?,
            data["delivery_dni"] as String?,
            data["delivery_note"] as Long?,
            data["delivery_person"] as String?,
            data["lot"] as String?,
            data["notes"] as String?,
            data["order"] as Map<String, Map<String, Number>>,
            data["order_datetime"] as Timestamp,
            data["order_id"] as Long,
            data["paid"] as Boolean,
            data["payment_method"] as Long,
            data["status"] as Long,
            data["total_price"] as Number?,
            documentId
        )
    }

    // para conseguir timestamp: Timestamp(System.currentTimeMillis()),

    fun getOrderDataGridModel(orderData: OrderData) : List<GridTextItemModel> {

        val dbOrderModel = orderDataToBDOrderModel(orderData)

        return listOf(
            GridTextItemModel(0,
                true, "XL"
            ),
            GridTextItemModel(1,
                true, "Docena:"
            ),
            GridTextItemModel(2,
                false, null, false, response = dbOrderModel.xlDozenQuantity.toString() + "   uds."
            ),
            GridTextItemModel(3,
                true, (dbOrderModel.xlDozenPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemModel(4,
                true, "Caja:"
            ),
            GridTextItemModel(5,
                false, null, false, response = dbOrderModel.xlBoxQuantity.toString() + "   uds."
            ),
            GridTextItemModel(6,
                true, (dbOrderModel.xlBoxPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemModel(7,
                true, "L"
            ),
            GridTextItemModel(8,
                true, "Docena:"
            ),
            GridTextItemModel(9,
                false, null, false, response = dbOrderModel.lDozenQuantity.toString() + "   uds."
            ),
            GridTextItemModel(10,
                true, (dbOrderModel.lDozenPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemModel(11,
                true, "Caja:"
            ),
            GridTextItemModel(12,
                false, null, false, response = dbOrderModel.lBoxQuantity.toString() + "   uds."
            ),
            GridTextItemModel(13,
                true, (dbOrderModel.lBoxPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemModel(14,
                true, "M"
            ),
            GridTextItemModel(15,
                true, "Docena:"
            ),
            GridTextItemModel(16,
                false, null, false, response = dbOrderModel.mDozenQuantity.toString() + "   uds."
            ),
            GridTextItemModel(17,
                true, (dbOrderModel.mDozenPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemModel(18,
                true, "Caja:"
            ),
            GridTextItemModel(19,
                false, null, false, response = dbOrderModel.mBoxQuantity.toString() + "   uds."
            ),
            GridTextItemModel(20,
                true, (dbOrderModel.mBoxPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemModel(21,
                true, "S"
            ),
            GridTextItemModel(22,
                true, "Docena:"
            ),
            GridTextItemModel(23,
                false, null, false, response = dbOrderModel.sDozenQuantity.toString() + "   uds."
            ),
            GridTextItemModel(24,
                true, (dbOrderModel.sDozenPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemModel(25,
                true, "Caja:"
            ),
            GridTextItemModel(26,
                false, null, false, response = dbOrderModel.sBoxQuantity.toString() + "   uds."
            ),
            GridTextItemModel(27,
                true, (dbOrderModel.sBoxPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
        )
    }

    fun getNewOrderGridModel(eggPricesData: EggPricesData) : List<GridTextItemModel> {
        return listOf(
            GridTextItemModel(0,
                true, "XL"
            ),
            GridTextItemModel(1,
                true, "Docena:"
            ),
            GridTextItemModel(2,
                false, null, response = ""
            ),
            GridTextItemModel(3,
                true, (eggPricesData.xlDozen ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemModel(4,
                true, "Caja:"
            ),
            GridTextItemModel(5,
                false, null, response = ""
            ),
            GridTextItemModel(6,
                true,  (eggPricesData.xlBox ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemModel(7,
                true, "L"
            ),
            GridTextItemModel(8,
                true, "Docena:"
            ),
            GridTextItemModel(9,
                false, null, response = ""
            ),
            GridTextItemModel(10,
                true, (eggPricesData.lDozen ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemModel(11,
                true, "Caja:"
            ),
            GridTextItemModel(12,
                false, null, response = ""
            ),
            GridTextItemModel(13,
                true, (eggPricesData.lBox ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemModel(14,
                true, "M"
            ),
            GridTextItemModel(15,
                true, "Docena:"
            ),
            GridTextItemModel(16,
                false, null, response = ""
            ),
            GridTextItemModel(17,
                true, (eggPricesData.mDozen ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemModel(18,
                true, "Caja:"
            ),
            GridTextItemModel(19,
                false, null, response = ""
            ),
            GridTextItemModel(20,
                true, (eggPricesData.mBox ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemModel(21,
                true, "S"
            ),
            GridTextItemModel(22,
                true, "Docena:"
            ),
            GridTextItemModel(23,
                false, null, response = ""
            ),
            GridTextItemModel(24,
                true, (eggPricesData.sDozen ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemModel(25,
                true, "Caja:"
            ),
            GridTextItemModel(26,
                false, null, response = ""
            ),
            GridTextItemModel(27,
                true, (eggPricesData.sBox ?: "-").toString() + " €/ud", isTextLeft = false
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

    fun getOrderSummary(dbOrderFieldData: DBOrderFieldData) : String {

        val list : MutableList<String> = mutableListOf()

        if (dbOrderFieldData.xlBoxQuantity != 0) list.add(dbOrderFieldData.xlBoxQuantity.toString() + " cajas XL")
        if (dbOrderFieldData.xlDozenQuantity != 0) list.add(dbOrderFieldData.xlDozenQuantity.toString() + " docenas XL")
        if (dbOrderFieldData.lBoxQuantity != 0) list.add(dbOrderFieldData.lBoxQuantity.toString() + " cajas L")
        if (dbOrderFieldData.lDozenQuantity != 0) list.add(dbOrderFieldData.lDozenQuantity.toString() + " docenas L")
        if (dbOrderFieldData.mBoxQuantity != 0) list.add(dbOrderFieldData.mBoxQuantity.toString() + " cajas M")
        if (dbOrderFieldData.mDozenQuantity != 0) list.add(dbOrderFieldData.mDozenQuantity.toString() + " docenas M")
        if (dbOrderFieldData.sBoxQuantity != 0) list.add(dbOrderFieldData.sBoxQuantity.toString() + " cajas S")
        if (dbOrderFieldData.sDozenQuantity != 0) list.add(dbOrderFieldData.sDozenQuantity.toString() + " docenas S")

        var summary = ""
        for (item in list) {
            summary += item
            if (item != list[list.size - 1]) summary += " - "
        }

        return summary
    }

    fun parseDBOrderFieldDataToMap(dbOrderFieldData: DBOrderFieldData) :
            Map<String, Map<String, Number?>> {
        val map = mutableMapOf<String, Map<String, Number?>>()
        if(dbOrderFieldData.xlBoxQuantity != null) {
            map["xl_box"] = mapOf(
                "price" to dbOrderFieldData.xlBoxPrice,
                "quantity" to dbOrderFieldData.xlBoxQuantity as Int
            )
        }
        if(dbOrderFieldData.xlDozenQuantity != null) {
            map["xl_dozen"] = mapOf(
                "price" to dbOrderFieldData.xlDozenPrice,
                "quantity" to dbOrderFieldData.xlDozenQuantity as Int
            )
        }
        if(dbOrderFieldData.lBoxQuantity != null) {
            map["l_box"] = mapOf(
                "price" to dbOrderFieldData.lBoxPrice,
                "quantity" to dbOrderFieldData.lBoxQuantity as Int
            )
        }
        if(dbOrderFieldData.lDozenQuantity != null) {
            map["l_dozen"] = mapOf(
                "price" to dbOrderFieldData.lDozenPrice,
                "quantity" to dbOrderFieldData.lDozenQuantity as Int
            )
        }
        if(dbOrderFieldData.mBoxQuantity != null) {
            map["m_box"] = mapOf(
                "price" to dbOrderFieldData.mBoxPrice,
                "quantity" to dbOrderFieldData.mBoxQuantity as Int
            )
        }
        if(dbOrderFieldData.mDozenQuantity != null) {
            map["m_dozen"] = mapOf(
                "price" to dbOrderFieldData.mDozenPrice,
                "quantity" to dbOrderFieldData.mDozenQuantity as Int
            )
        }
        if(dbOrderFieldData.sBoxQuantity != null) {
            map["s_box"] = mapOf(
                "price" to dbOrderFieldData.sBoxPrice,
                "quantity" to dbOrderFieldData.sBoxQuantity as Int
            )
        }
        if(dbOrderFieldData.sDozenQuantity != null) {
            map["s_dozen"] = mapOf(
                "price" to dbOrderFieldData.sDozenPrice,
                "quantity" to dbOrderFieldData.sDozenQuantity as Int
            )
        }
        return map
    }

    fun getOrderStructure(recyclerView: RecyclerView) : DBOrderFieldData? {
        val xlDozenValue : Int?
        val xlDozenPrice : Double?
        val xlBoxValue : Int?
        val xlBoxPrice : Double?
        val lDozenValue : Int?
        val lDozenPrice : Double?
        val lBoxValue : Int?
        val lBoxPrice : Double?
        val mDozenValue : Int?
        val mDozenPrice : Double?
        val mBoxValue : Int?
        val mBoxPrice : Double?
        val sDozenValue : Int?
        val sDozenPrice : Double?
        val sBoxValue : Int?
        val sBoxPrice : Double?
        with(recyclerView.adapter as HNGridTextAdapter) {
            xlDozenValue =
                if(this.getItemWithPosition(2).response.toString().toIntOrNull() == 0) null
                else this.getItemWithPosition(2).response.toString().toIntOrNull()
            xlDozenPrice =
                if(this.getItemWithPosition(3).text!!.split(" ")[0].toDoubleOrNull() == 0.0) null
                else this.getItemWithPosition(3).text!!.split(" ")[0].toDoubleOrNull()
            xlBoxValue =
                if(this.getItemWithPosition(5).response.toString().toIntOrNull() == 0) null
                else this.getItemWithPosition(5).response.toString().toIntOrNull()
            xlBoxPrice =
                if(this.getItemWithPosition(6).text!!.split(" ")[0].toDoubleOrNull() == 0.0) null
                else this.getItemWithPosition(6).text!!.split(" ")[0].toDoubleOrNull()
            lDozenValue =
                if(this.getItemWithPosition(9).response.toString().toIntOrNull() == 0) null
                else this.getItemWithPosition(9).response.toString().toIntOrNull()
            lDozenPrice =
                if(this.getItemWithPosition(10).text!!.split(" ")[0].toDoubleOrNull() == 0.0) null
                else this.getItemWithPosition(10).text!!.split(" ")[0].toDoubleOrNull()
            lBoxValue =
                if(this.getItemWithPosition(12).response.toString().toIntOrNull() == 0) null
                else this.getItemWithPosition(12).response.toString().toIntOrNull()
            lBoxPrice =
                if(this.getItemWithPosition(13).text!!.split(" ")[0].toDoubleOrNull() == 0.0) null
                else this.getItemWithPosition(13).text!!.split(" ")[0].toDoubleOrNull()
            mDozenValue =
                if(this.getItemWithPosition(16).response.toString().toIntOrNull() == 0) null
                else this.getItemWithPosition(16).response.toString().toIntOrNull()
            mDozenPrice =
                if(this.getItemWithPosition(17).text!!.split(" ")[0].toDoubleOrNull() == 0.0) null
                else this.getItemWithPosition(17).text!!.split(" ")[0].toDoubleOrNull()
            mBoxValue =
                if(this.getItemWithPosition(19).response.toString().toIntOrNull() == 0) null
                else this.getItemWithPosition(19).response.toString().toIntOrNull()
            mBoxPrice =
                if(this.getItemWithPosition(20).text!!.split(" ")[0].toDoubleOrNull() == 0.0) null
                else this.getItemWithPosition(20).text!!.split(" ")[0].toDoubleOrNull()
            sDozenValue =
                if(this.getItemWithPosition(23).response.toString().toIntOrNull() == 0) null
                else this.getItemWithPosition(23).response.toString().toIntOrNull()
            sDozenPrice =
                if(this.getItemWithPosition(24).text!!.split(" ")[0].toDoubleOrNull() == 0.0) null
                else this.getItemWithPosition(24).text!!.split(" ")[0].toDoubleOrNull()
            sBoxValue =
                if(this.getItemWithPosition(26).response.toString().toIntOrNull() == 0) null
                else this.getItemWithPosition(26).response.toString().toIntOrNull()
            sBoxPrice =
                if(this.getItemWithPosition(27).text!!.split(" ")[0].toDoubleOrNull() == 0.0) null
                else this.getItemWithPosition(27).text!!.split(" ")[0].toDoubleOrNull()
        }

        if (xlDozenValue != null || xlBoxValue != null || lDozenValue != null
            || lBoxValue != null || mDozenValue != null || mBoxValue != null ||
            sDozenValue != null || sBoxValue != null) {
            try {
                return DBOrderFieldData(
                    xlBoxPrice = xlBoxPrice as Double?,
                    xlBoxQuantity = xlBoxValue as Int?,
                    xlDozenPrice = xlDozenPrice as Double?,
                    xlDozenQuantity = xlDozenValue as Int?,
                    lBoxPrice = lBoxPrice as Double?,
                    lBoxQuantity = lBoxValue as Int?,
                    lDozenPrice = lDozenPrice as Double?,
                    lDozenQuantity = lDozenValue as Int?,
                    mBoxPrice = mBoxPrice as Double?,
                    mBoxQuantity = mBoxValue as Int?,
                    mDozenPrice = mDozenPrice as Double?,
                    mDozenQuantity = mDozenValue as Int?,
                    sBoxPrice = sBoxPrice as Double?,
                    sBoxQuantity = sBoxValue as Int?,
                    sDozenPrice = sDozenPrice as Double?,
                    sDozenQuantity = sDozenValue as Int?,
                )
            } catch (e: Exception) {
                return null
            }
        } else {
            return null
        }
    }

    fun getTotalPrice(dbOrderFieldData: DBOrderFieldData) : Double {

        var totalPrice: Double = 0.0
        with(dbOrderFieldData) {
            if (xlBoxQuantity != null && xlBoxPrice != null) {
                totalPrice += (xlBoxQuantity!! as Int) * (xlBoxPrice!! as Double)
            }
            if (xlDozenQuantity != null && xlDozenPrice != null) {
                totalPrice += (xlDozenQuantity!! as Int) * (xlDozenPrice!! as Double)
            }
            if (lBoxQuantity != null && lBoxPrice != null) {
                totalPrice += (lBoxQuantity!! as Int) * (lBoxPrice!! as Double)
            }
            if (lDozenQuantity != null && lDozenPrice != null) {
                totalPrice += (lDozenQuantity!! as Int) * (lDozenPrice!! as Double)
            }
            if (mBoxQuantity != null && mBoxPrice != null) {
                totalPrice += (mBoxQuantity!! as Int) * (mBoxPrice!! as Double)
            }
            if (mDozenQuantity != null && mDozenPrice != null) {
                totalPrice += (mDozenQuantity!! as Int) * (mDozenPrice!! as Double)
            }
            if (sBoxQuantity != null && sBoxPrice != null) {
                totalPrice += (sBoxQuantity!! as Int) * (sBoxPrice!! as Double)
            }
            if (sDozenQuantity != null && sDozenPrice != null) {
                totalPrice += (sDozenQuantity!! as Int) * (sDozenPrice!! as Double)
            }
        }
        val decimal = BigDecimal(3.14159265359).setScale(2, RoundingMode.HALF_EVEN)
        println(decimal)

        (totalPrice * 100).roundToInt() / 100

        return ((totalPrice * 100).roundToInt()).toDouble() / 100
    }

}