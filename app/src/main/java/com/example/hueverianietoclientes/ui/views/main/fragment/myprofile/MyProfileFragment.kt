package com.example.hueverianietoclientes.ui.views.main.fragment.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hueverianietoclientes.base.BaseFragment
import com.example.hueverianietoclientes.base.BaseState
import com.example.hueverianietoclientes.databinding.FragmentMyProfileBinding
import com.example.hueverianietoclientes.ui.views.main.MainActivity

class MyProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentMyProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav("Mi perfil", true)
        this.binding = FragmentMyProfileBinding.inflate(inflater, container, false)
        return this.binding.root
    }

    override fun configureUI() {
        this.binding.saveCancelButtonLinearLayout.visibility = View.GONE
        this.binding.modifyButton.setText("MODIFICAR")
        this.binding.saveButton.setText("GUARDAR")
        this.binding.cancelButton.setText("CANCELAR")
    }

    override fun setObservers() {
        //TODO("Not yet implemented")
    }

    override fun setListeners() {
    }

    override fun updateUI(state: BaseState) {
        //TODO("Not yet implemented")
    }
}