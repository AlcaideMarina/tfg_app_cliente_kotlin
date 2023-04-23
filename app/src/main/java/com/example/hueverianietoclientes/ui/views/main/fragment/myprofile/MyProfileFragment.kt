package com.example.hueverianietoclientes.ui.views.main.fragment.myprofile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.hueverianietoclientes.base.BaseFragment
import com.example.hueverianietoclientes.base.BaseState
import com.example.hueverianietoclientes.databinding.FragmentMyProfileBinding
import com.example.hueverianietoclientes.ui.views.login.LoginActivity
import com.example.hueverianietoclientes.ui.views.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

    companion object {
        private val TAG = MyProfileFragment::class.java.simpleName
    }
}