package com.example.hueverianietoclientes.ui.views.login

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianietoclientes.base.BaseActivity
import com.example.hueverianietoclientes.base.core.Event
import com.example.hueverianietoclientes.data.network.ClientData
import com.example.hueverianietoclientes.data.network.ClientLoginData
import com.example.hueverianietoclientes.data.network.LoginResponse
import com.example.hueverianietoclientes.domain.usecase.GetClientDataUseCase
import com.example.hueverianietoclientes.domain.usecase.LoginUseCase
import com.example.hueverianietoclientes.ui.views.main.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginUseCase: LoginUseCase,
    val getClientDataUseCase: GetClientDataUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(LoginViewState())
    val viewState: StateFlow<LoginViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(ClientLoginData())
    val alertDialog: LiveData<ClientLoginData> get() = _alertDialog

    private var _navigateToMainActivity = MutableLiveData<Event<Boolean>>()
    val navigateToMainActivity: LiveData<Event<Boolean>> get() = _navigateToMainActivity

    private var _clientData = MutableLiveData<ClientData>()
    val clientData: LiveData<ClientData> get() = _clientData

    private fun checkValidEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun login(email: String, password: String) {
        if (checkValidEmail(email)) {
            viewModelScope.launch {
                _viewState.value = LoginViewState(isLoading = true)
                when (val result = loginUseCase(email, password)) {
                    LoginResponse.Error -> {
                        _alertDialog.value = ClientLoginData(email, password, true)
                        _viewState.value = LoginViewState(false)
                    }
                    is LoginResponse.Success -> {
                        when (val client = getClientDataUseCase(result.uid)) {
                            null -> {
                                _alertDialog.value = ClientLoginData(email, password, true)
                                _viewState.value = LoginViewState(false)
                            }
                            else -> {
                                if (!client.deleted && client.hasAccount) {
                                    _clientData.value = client
                                    _navigateToMainActivity.value = Event(true)
                                } else {
                                    _alertDialog.value = ClientLoginData(email, password, true)
                                    _viewState.value = LoginViewState(false)
                                }
                            }
                        }
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

    fun navigateToMainActivity(context: Context, clientData: Parcelable) {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("client_data", clientData)
        context.startActivity(intent)
        (context as BaseActivity).finish()
    }

}
