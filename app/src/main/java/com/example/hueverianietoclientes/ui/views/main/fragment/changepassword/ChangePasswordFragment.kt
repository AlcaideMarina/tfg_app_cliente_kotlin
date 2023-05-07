package com.example.hueverianietoclientes.ui.views.main.fragment.changepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.hueverianietoclientes.base.BaseFragment
import com.example.hueverianietoclientes.base.BaseState
import com.example.hueverianietoclientes.data.network.ClientData
import com.example.hueverianietoclientes.databinding.FragmentChangePasswordBinding
import com.example.hueverianietoclientes.ui.views.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment() {

    private lateinit var binding : FragmentChangePasswordBinding
    private lateinit var clientData: ClientData
    private val changePasswordViewModel : ChangePasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav(true)
        this.binding = FragmentChangePasswordBinding.inflate(
            inflater, container, false
        )
        val args : ChangePasswordFragmentArgs by navArgs()
        this.clientData = args.clientData
        return this.binding.root
    }

    override fun configureUI() {
        this.binding.saveButton.setText("Cambiar contraseña")
    }

    override fun setObservers() {
        //TODO("Not yet implemented")
    }

    override fun setListeners() {
        this.binding.saveButton.setOnClickListener {
            this.changePasswordViewModel.checkOldPassword(this.binding.oldPasswordTextInputLayout.text.toString())
        }
    }

    override fun updateUI(state: BaseState) {
        //TODO("Not yet implemented")
    }
}