package com.example.hueverianietoclientes.ui.views.main.fragment.myorders

import com.example.hueverianietoclientes.base.BaseState

class MyOrdersViewState(
    var isLoading: Boolean = false,
    var error: Boolean = false,
    var isEmpty: Boolean = false
) : BaseState
