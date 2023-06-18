package com.example.hueverianietoclientes.utils

import com.example.hueverianietoclientes.data.network.ClientData

object ClientUtils {

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
            data["id"] as Long,
            data["phone"] as List<Map<String, Long>>,
            data["postal_code"] as Long,
            data["province"] as String,
            data["uid"] as String,
            data["user"] as String,
            documentId
        )
    }

}
