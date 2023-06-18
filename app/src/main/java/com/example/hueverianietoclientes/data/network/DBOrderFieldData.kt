package com.example.hueverianietoclientes.data.network

data class DBOrderFieldData(
    // XL - box
    var xlBoxPrice: Number? = null,
    var xlBoxQuantity: Number? = 0,
    // XL - dozen
    var xlDozenPrice: Number? = null,
    var xlDozenQuantity: Number? = 0,
    // L - box
    var lBoxPrice: Number? = null,
    var lBoxQuantity: Number? = 0,
    // L - dozen
    var lDozenPrice: Number? = null,
    var lDozenQuantity: Number? = 0,
    // M - box
    var mBoxPrice: Number? = null,
    var mBoxQuantity: Number? = 0,
    // M - dozen
    var mDozenPrice: Number? = null,
    var mDozenQuantity: Number? = 0,
    // S - box
    var sBoxPrice: Number? = null,
    var sBoxQuantity: Number? = 0,
    // S - dozen
    var sDozenPrice: Number? = null,
    var sDozenQuantity: Number? = 0,
)
