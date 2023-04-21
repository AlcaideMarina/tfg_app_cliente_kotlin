package com.example.hueverianietoclientes.ui.views.login

import android.service.autofill.UserData
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianietoclientes.core.Event
import com.example.hueverianietoclientes.data.network.ClientData
import com.example.hueverianietoclientes.data.network.ClientLoginData
import com.example.hueverianietoclientes.data.network.LoginResponse
import com.example.hueverianietoclientes.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (val loginUseCase: LoginUseCase) : ViewModel() {

    private val _viewState = MutableStateFlow(LoginViewState())
    val viewState: StateFlow<LoginViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(ClientLoginData())
    val alertDialog: LiveData<ClientLoginData> get() = _alertDialog

    private var _navigateToMainActivity = MutableLiveData<Event<Boolean>>()
    val navigateToMainActivity: LiveData<Event<Boolean>> get() = _navigateToMainActivity

    private fun checkValidEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun login(email: String, password: String) {
        if (checkValidEmail(email)) {
            viewModelScope.launch {
                _viewState.value = LoginViewState(isLoading = true)
                when(val result = loginUseCase(email, password)) {
                    LoginResponse.Error -> {
                        _alertDialog.value = ClientLoginData(email, password, true)
                        _viewState.value = LoginViewState(false)
                    }
                    LoginResponse.Success -> {
                        _navigateToMainActivity.value = Event(true)
                    }
                }
            }
        } else {
            _viewState.value = LoginViewState(
                isValidEmail = checkValidEmail(email)
            )
        }
        _viewState.value = LoginViewState(isLoading = false)
    }

    fun navigateToMainActivity() {

    }

}
