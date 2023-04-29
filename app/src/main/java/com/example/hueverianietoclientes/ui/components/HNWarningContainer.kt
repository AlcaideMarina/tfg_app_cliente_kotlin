package com.example.hueverianietoclientes.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.hueverianietoclientes.base.BaseComponent
import com.example.hueverianietoclientes.databinding.ComponentWarningContainerBinding

class HNWarningContainer : ConstraintLayout, BaseComponent {

    private var binding: ComponentWarningContainerBinding = ComponentWarningContainerBinding.inflate(
        LayoutInflater.from(this.context)
    )

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        this.addView(this.binding.root)
    }

    override fun getComponentContext(): Context {
        return this.context
    }

    override fun getView(): View {
        return this
    }

    fun setTitle(text: String) {
        this.binding.containerTitle.text = text
    }

    fun setText(text: String) {
        this.binding.containerText.text = text
    }
}
