package com.example.hueverianietoclientes.ui.views.main.fragment.billing

import com.example.hueverianietoclientes.base.BaseState

class BillingViewState(
    var isLoading: Boolean = false,
    var error: Boolean = false,
    var isEmpty: Boolean = false
) : BaseState
