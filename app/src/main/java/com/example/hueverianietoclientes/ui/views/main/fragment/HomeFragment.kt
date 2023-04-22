package com.example.hueverianietoclientes.ui.views.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianietoclientes.R
import com.example.hueverianietoclientes.base.BaseFragment
import com.example.hueverianietoclientes.base.BaseState
import com.example.hueverianietoclientes.databinding.FragmentHomeBinding
import com.example.hueverianietoclientes.domain.model.GridItemModel
import com.example.hueverianietoclientes.ui.components.hngridview.CustomGridLayoutManager
import com.example.hueverianietoclientes.ui.components.hngridview.HNGridViewAdapter
import com.example.hueverianietoclientes.ui.views.main.MainActivity


class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav("Home")
        this.binding = FragmentHomeBinding
            .inflate(inflater, container, false)
        return this.binding.root
    }

    override fun configureUI() {
        setMenuRecyclerView()
    }

    override fun setObservers() {
        // It is not necessary, this is a static view
    }

    override fun setListeners() {
        //TODO("Not yet implemented")
    }

    override fun updateUI(state: BaseState) {
        // It is not necessary, this is a static view
    }

    private fun showToasst(msg : String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    private fun setMenuRecyclerView() {
        val menuOptions: List<GridItemModel> = listOf(
            GridItemModel("Mi perfil", AppCompatResources.getDrawable(requireContext(), R.drawable.ic_launcher_foreground)!!) { showToasst("Pulsado 'Mi perfil'") },
            GridItemModel("Facturación", AppCompatResources.getDrawable(requireContext(), R.drawable.ic_launcher_foreground)!!) { showToasst("Pulsado 'Facturación'") },
            GridItemModel("Mis pedidos", AppCompatResources.getDrawable(requireContext(), R.drawable.ic_launcher_foreground)!!) { showToasst("Pulsado 'Mis pedidos'") },
            GridItemModel("Nuevo pedido", AppCompatResources.getDrawable(requireContext(), R.drawable.ic_launcher_foreground)!!) { showToasst("Pulsado 'Nuevo pedido'") },
            GridItemModel("Ajustes", AppCompatResources.getDrawable(requireContext(), R.drawable.ic_launcher_foreground)!!) { showToasst("Pulsado 'Ajustes'") }
        )
        val manager = CustomGridLayoutManager(this.context, 2)
        manager.setScrollEnabled(false)
        manager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (menuOptions.size % 2 != 0) {
                    if (position == menuOptions.size - 1) 2 else 1
                } else {
                    1
                }
            }
        }

        with(this.binding.lstItems) {
            layoutManager = manager
            adapter = HNGridViewAdapter(menuOptions)
        }
    }

}
