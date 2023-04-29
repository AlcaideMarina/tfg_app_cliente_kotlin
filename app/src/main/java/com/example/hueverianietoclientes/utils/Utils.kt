package com.example.hueverianietoclientes.utils

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.view.View
import com.example.hueverianietoclientes.domain.model.ModalDialogModel
import com.example.hueverianietoclientes.ui.components.HNModalDialog
import com.google.firebase.Timestamp
import java.util.*

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

    fun parseStringToTimestamp(dateStr : String, pattern : String? = "dd-MM-yyyy") :
            Timestamp = Timestamp(SimpleDateFormat(pattern).parse(dateStr))

    fun parseDateToString(date: Date) :
            String = SimpleDateFormat("dd/MM/yyyy").format(date)


    fun addDaysToDate(date: Date, daysToAdd: Int) : Date {
        val c = Calendar.getInstance()
        c.time = date
        c.add(Calendar.DATE, daysToAdd)
        return c.time
    }

    fun setPopUp(alertDialog: HNModalDialog, context: Context, title: String,
                         message: String, leftButton: String, rightButton: String?,
                         leftButtonListener: View.OnClickListener,
                         rightButtonListener: View.OnClickListener?) {
        alertDialog.show(
            context,
            ModalDialogModel(
                title,
                message,
                leftButton,
                rightButton,
                leftButtonListener,
                rightButtonListener,
                true
            )
        )
    }

}