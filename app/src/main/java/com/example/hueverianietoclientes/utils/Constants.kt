package com.example.hueverianietoclientes.utils

import com.example.hueverianietoclientes.R

object Constants {

    const val loginNetworkError: String =
        "A network error (such as timeout, interrupted connection or unreachable host) has occurred."
    const val loginBadFormattedEmailError: String = "The email address is badly formatted."
    const val loginNoUserRecordedError: String =
        "There is no user record corresponding to this identifier. The user may have been deleted."
    const val loginInvalidPasswordError: String =
        "The password is invalid or the user does not have a password."

    val typeEggUnits : Map<Int, Int> = mapOf(
        R.string.dozen_singular_units to 0,
        R.string.box_singular_units to 1
    )

    val paymentMethod : Map<Int, Int> = mapOf(
        R.string.in_cash to 0,
        R.string.per_receipt to 1,
        R.string.transfer to 2
    )

    val orderStatus : Map<Int, Int> = mapOf(
        R.string.price_pending to 0,
        R.string.backordered to 1,
        R.string.in_delivery to 2,
        R.string.delivered to 3,
        R.string.delivery_attempt to 4,
        R.string.cancelled to 5
    )

    const val dateFormat = "dd/MM/yyyy"

}