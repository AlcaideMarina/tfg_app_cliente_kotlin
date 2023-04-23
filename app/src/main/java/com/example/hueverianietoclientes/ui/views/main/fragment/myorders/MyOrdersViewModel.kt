package com.example.hueverianietoclientes.ui.views.main.fragment.myorders

import androidx.lifecycle.ViewModel
import com.example.hueverianietoclientes.domain.usecase.MyOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyOrdersViewModel @Inject constructor(
    val myOrdersUseCase: MyOrdersUseCase
) : ViewModel() {
}