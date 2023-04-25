package com.example.hueverianietoclientes.ui.components.hngridview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.hueverianietoclientes.base.BaseComponent
import com.example.hueverianietoclientes.databinding.ComponentGridTextBinding

class HNGridText : ConstraintLayout, BaseComponent {

    private val binding: ComponentGridTextBinding = ComponentGridTextBinding.inflate(
        LayoutInflater.from(this.context)
    )

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )/*

    init {
        this.addView(this.binding.root)
    }*/

    override fun getComponentContext(): Context {
        return context
    }

    override fun getView(): View {
        return this
    }

}