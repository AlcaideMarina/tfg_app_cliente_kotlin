package com.example.hueverianietoclientes.ui.components

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.hueverianietoclientes.R
import com.example.hueverianietoclientes.databinding.ComponentItemSettingsBinding
import com.example.hueverianietoclientes.domain.model.SimpleListModel

class HNSimpleListAdapter(context: Context, dataArrayList: ArrayList<SimpleListModel?>)
    : ArrayAdapter<SimpleListModel>(context, R.layout.component_item_settings, dataArrayList) {

    var binding : ComponentItemSettingsBinding = ComponentItemSettingsBinding.inflate(
        LayoutInflater.from(context)
    )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view = convertView
        val item = getItem(position)

        if (view == null) {
            view = LayoutInflater.from(context).inflate(
                R.layout.component_item_settings, parent, false)
        }

        val titleView = view!!.findViewById<TextView>(R.id.title_item_list)
        val containerView = view.findViewById<LinearLayout>(R.id.container)
        titleView.text = item?.title
        containerView.setOnClickListener(item?.onClickListener)

        return view

    }

}
