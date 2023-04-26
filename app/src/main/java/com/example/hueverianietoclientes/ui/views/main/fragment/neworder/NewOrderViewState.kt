package com.example.hueverianietoclientes.ui.views.main.fragment.neworder

import com.example.hueverianietoclientes.base.BaseState

class NewOrderViewState (
    val error: Boolean = false,
    val isLoading: Boolean = false,
    val step: Int = 1,
    val onClickConfirm: Boolean = false
) : BaseState
