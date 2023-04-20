package com.example.hueverianietoclientes.utils

import android.content.res.Resources
import android.util.TypedValue

object Conversions {

    fun convertPxToDp(px: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            px.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }

}
