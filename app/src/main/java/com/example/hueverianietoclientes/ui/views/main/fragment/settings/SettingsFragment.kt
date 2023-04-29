package com.example.hueverianietoclientes.ui.views.main.fragment.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hueverianietoclientes.base.BaseFragment
import com.example.hueverianietoclientes.base.BaseState
import com.example.hueverianietoclientes.databinding.FragmentSettingsBinding
import com.example.hueverianietoclientes.domain.model.SimpleListModel
import com.example.hueverianietoclientes.ui.components.HNSimpleListAdapter
import com.example.hueverianietoclientes.ui.views.main.MainActivity

class SettingsFragment : BaseFragment() {

    private lateinit var binding: FragmentSettingsBinding
    private var dataArrayList = ArrayList<SimpleListModel?>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav(true)
        this.binding = FragmentSettingsBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        dataArrayList = arrayListOf(
            SimpleListModel(
                title = "Cambiar contraseña",
                onClickListener = { }
            ),
            SimpleListModel(
                title = "Cambiar idioma",
                onClickListener = { }
            )
        )
        this.binding.settingOptionsListView.adapter = HNSimpleListAdapter(requireContext(),
            dataArrayList)
        this.binding.settingOptionsListView.isClickable = true

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
