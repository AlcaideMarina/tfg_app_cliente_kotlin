package com.example.hueverianietoclientes.ui.views.main

import androidx.lifecycle.ViewModel
import com.example.hueverianietoclientes.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val homeUseCase: HomeUseCase) : ViewModel() {

}
