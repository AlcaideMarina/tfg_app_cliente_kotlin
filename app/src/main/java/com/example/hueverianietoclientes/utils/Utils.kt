package com.example.hueverianietoclientes.utils

import android.icu.text.SimpleDateFormat
import com.google.firebase.Timestamp

object Utils {

    fun <K, V> getKey(map: Map<K, V>, target: V): K? {
        for ((key, value) in map) {
            if (target == value) {
                return key
            }
        }
        return null
    }

    fun parseTimestampToString(timestamp: Timestamp?) : String? {
        return if(timestamp == null) {
            null
        } else {
            SimpleDateFormat("MM/dd/yyyy").format(timestamp.toDate())
        }
    }

}