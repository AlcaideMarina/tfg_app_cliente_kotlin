package com.example.hueverianietoclientes.domain.model

class GridTextItemModel(
    val id: Int,
    val isTextView: Boolean,
    val text: String,
    var isEnabled: Boolean = true,
    val isTextLeft: Boolean = true,
    var response: Any? = null
)