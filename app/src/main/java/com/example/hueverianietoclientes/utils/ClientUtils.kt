package com.example.hueverianietoclientes.utils

import android.text.InputType
import com.example.hueverianietoclientes.data.network.ClientData

object ClientUtils {

    fun checkErrorMap(data: MutableMap<String, Any?>?) : String? {
        return if (data == null) {
            "empty input map"
        } else if (data.containsKey("cif") && data.containsKey("city") &&
            data.containsKey("created_by") && data.containsKey("deleted") &&
            data.containsKey("company") && data.containsKey("direction") && data.containsKey("email")
            && data.containsKey("email_account") && data.containsKey("has_account") && data.containsKey("id") &&
            data.containsKey("phone") && data.containsKey("postal_code") &&
            data.containsKey("province") && data.containsKey("uid") &&
            data.containsKey("user")
        ) {
            null
        } else {
            "missing fields"
        }
    }

    // TODO: Investigar cómo comprobar si data["phone"] es List<Map<String, Long>>
    fun mapToParcelable(data: MutableMap<String, Any?>, documentId: String): ClientData {
        return ClientData(
            data["cif"] as String,
            data["city"] as String,
            data["created_by"] as String,
            data["company"] as String,
            data["deleted"] as Boolean,
            data["direction"] as String,
            data["email"] as String,
            data["has_account"] as Boolean,
            data["id"] as String,
            data["phone"] as List<Map<String, Long>>,
            data["postal_code"] as Long,
            data["province"] as String,
            data["uid"] as String,
            data["user"] as String,
            documentId
        )
    }

    fun parcelableToMap(clientData: ClientData) : MutableMap<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        map["cif"] = clientData.cif
        map["city"] = clientData.city
        map["company"] = clientData.company
        map["created_by"] = clientData.createdBy
        map["deleted"] = clientData.deleted
        map["direction"] = clientData.direction
        map["email"] = clientData.email
        map["has_account"] = clientData.hasAccount
        map["id"] = clientData.id
        map["phone"] = clientData.phone
        map["postal_code"] = clientData.postalCode
        map["province"] = clientData.province
        map["uid"] = clientData.uid
        map["user"] = clientData.user
        return map
    }

}