package com.example.hueverianietoclientes.ui.views.main.fragment.neworder

import com.example.hueverianietoclientes.base.BaseState

class NewOrderViewState(
    val error: Boolean = false,
    val isLoading: Boolean = false,
    val step: Int = 1,
    val popUpCode: Int? = null
) : BaseState


/*
* Steps:
*   1       - Add new order
*   2       - Order summary
*   3       - All orders fragment
* */

/*
* PopUp Codes:
*   null    - there is no pop up to show
*   0       - Incorrect form
*   1       - Incorrect order form
*   2       - Warning step 1 to step 2
*   3       - Undefined error
*   4       - Error while saving in db
* */
