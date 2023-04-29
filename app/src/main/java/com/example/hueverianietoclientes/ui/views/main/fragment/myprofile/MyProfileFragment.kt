package com.example.hueverianietoclientes.ui.views.main.fragment.myprofile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.hueverianietoclientes.base.BaseFragment
import com.example.hueverianietoclientes.base.BaseState
import com.example.hueverianietoclientes.data.network.ClientData
import com.example.hueverianietoclientes.databinding.FragmentMyProfileBinding
import com.example.hueverianietoclientes.ui.views.login.LoginActivity
import com.example.hueverianietoclientes.ui.views.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentMyProfileBinding
    private lateinit var clientData: ClientData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav(true)
        this.binding = FragmentMyProfileBinding.inflate(inflater, container, false)

        val args: MyProfileFragmentArgs by navArgs()
        this.clientData = args.clientData

        return this.binding.root
    }

    override fun configureUI() {
        setDisabledTextFields()
        setTextFields()
    }

    override fun setObservers() {
        // It is not necessary, this is a static screen
    }

    override fun setListeners() {
        // It is not necessary, this is a static screen
    }

    override fun updateUI(state: BaseState) {
        // It is not necessary, this is a static screen
    }

    private fun setDisabledTextFields() {
        with(this.binding) {
            companyTextInputLayout.isEnabled = false
            directionTextInputLayout.isEnabled = false
            cityTextInputLayout.isEnabled = false
            provinceTextInputLayout.isEnabled = false
            postalCodeTextInputLayout.isEnabled = false
            cifTextInputLayout.isEnabled = false
            phoneTextInputLayoutPhone1.isEnabled = false
            phoneTextInputLayoutName1.isEnabled = false
            phoneTextInputLayoutPhone2.isEnabled = false
            phoneTextInputLayoutName2.isEnabled = false
            userTextInputLayout.isEnabled = false
            emailTextInputLayout.isEnabled = false
        }
    }

    private fun setTextFields() {
        val phone1 = clientData.phone[0].entries.iterator().next()
        val phone2 = clientData.phone[1].entries.iterator().next()
        with(this.binding) {
            companyTextInputLayout.setInputText(clientData.company)
            directionTextInputLayout.setInputText(clientData.direction)
            cityTextInputLayout.setInputText(clientData.city)
            provinceTextInputLayout.setInputText(clientData.province)
            postalCodeTextInputLayout.setInputText(clientData.postalCode.toString())
            cifTextInputLayout.setInputText(clientData.cif)
            phoneTextInputLayoutPhone1.setInputText(phone1.key)
            phoneTextInputLayoutName1.setInputText(phone1.value.toString())
            phoneTextInputLayoutPhone2.setInputText(phone2.key)
            phoneTextInputLayoutName2.setInputText(phone2.value.toString())
            userTextInputLayout.setInputText(clientData.user)
            emailTextInputLayout.setInputText(clientData.email)
        }
    }

    companion object {
        private val TAG = MyProfileFragment::class.java.simpleName
    }
}