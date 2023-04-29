package com.example.hueverianietoclientes.ui.views.main.fragment.changepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hueverianietoclientes.base.BaseFragment
import com.example.hueverianietoclientes.base.BaseState
import com.example.hueverianietoclientes.databinding.FragmentChangePasswordBinding
import com.example.hueverianietoclientes.ui.views.main.MainActivity

class ChangePasswordFragment : BaseFragment() {

    private lateinit var binding : FragmentChangePasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav(true)      // TODO: Probar esto - vuelve al fragment de home o de ajustes?
        this.binding = FragmentChangePasswordBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        //TODO("Not yet implemented")
    }

    override fun setObservers() {
        //TODO("Not yet implemented")
    }

    override fun setListeners() {
        //TODO("Not yet implemented")
    }

    override fun updateUI(state: BaseState) {
        //TODO("Not yet implemented")
    }
}