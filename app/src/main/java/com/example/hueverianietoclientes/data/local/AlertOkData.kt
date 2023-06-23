package com.example.hueverianietoclientes.data.local

data class AlertOkData(
    var title: String = "",
    var text: String = "",
    var finish: Boolean = false,
    var customCode: Int? = null
)
