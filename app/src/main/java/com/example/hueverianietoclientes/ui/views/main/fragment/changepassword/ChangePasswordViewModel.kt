package com.example.hueverianietoclientes.ui.views.main.fragment.changepassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianietoclientes.domain.usecase.ChangePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    val changePasswordUseCase: ChangePasswordUseCase
) : ViewModel() {

    fun checkOldPassword(oldPassword: String) {
        // TODO
    }

}
