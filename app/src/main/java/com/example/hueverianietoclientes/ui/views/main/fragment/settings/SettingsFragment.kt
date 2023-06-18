package com.example.hueverianietoclientes.ui.views.main.fragment.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.hueverianietoclientes.base.BaseFragment
import com.example.hueverianietoclientes.base.BaseState
import com.example.hueverianietoclientes.data.network.ClientData
import com.example.hueverianietoclientes.databinding.FragmentSettingsBinding
import com.example.hueverianietoclientes.domain.model.SimpleListModel
import com.example.hueverianietoclientes.ui.components.HNSimpleListAdapter
import com.example.hueverianietoclientes.ui.views.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment() {

    private lateinit var binding: FragmentSettingsBinding
    private var dataArrayList = ArrayList<SimpleListModel?>()
    private val settingsViewModel : SettingsViewModel by viewModels()
    private lateinit var clientData: ClientData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav(true)
        this.binding = FragmentSettingsBinding.inflate(
            inflater, container, false
        )
        val args : SettingsFragmentArgs by navArgs()
        this.clientData = args.clientData
        return this.binding.root
    }

    override fun configureUI() {
        dataArrayList = arrayListOf(
            SimpleListModel(
                title = "Cambiar contraseña",
                onClickListener = {
                    this.settingsViewModel.navigateToChangePassword(
                        view,
                        bundleOf(
                            "clientData" to clientData
                        )
                    )
                }
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
        // Not necessary
    }

    override fun setListeners() {
        // Not necessary
    }

    override fun updateUI(state: BaseState) {
        // Not necessary
    }

}
