package com.example.hueverianietoclientes.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.hueverianietoclientes.R
import com.example.hueverianietoclientes.base.BaseComponent
import com.example.hueverianietoclientes.databinding.ComponentDropdownBinding
import com.google.android.material.textfield.TextInputLayout

open class HNDropdown : CoordinatorLayout, BaseComponent {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    private var binding : ComponentDropdownBinding = ComponentDropdownBinding.bind(
        LayoutInflater
            .from(context)
            .inflate(R.layout.component_dropdown, this, true)
    )

    override fun getComponentContext(): Context {
        return this.context
    }

    override fun getView(): View {
        return this
    }

    private fun createArrayAdapter(itemList : List<String>) : ArrayAdapter<String> {
        return ArrayAdapter(context, R.layout.component_dropdown_list_item, itemList)
    }

    fun setAdapter(itemList : List<String>) {
        this.binding.autoCompleteTextView.setAdapter(createArrayAdapter(itemList))
    }

    fun setOnClickListener(listener: AdapterView.OnItemClickListener) {
        this.binding.autoCompleteTextView.onItemClickListener = listener
    }

}
