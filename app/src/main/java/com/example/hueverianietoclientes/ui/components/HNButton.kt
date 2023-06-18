package com.example.hueverianietoclientes.ui.components

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.hueverianietoclientes.R
import com.example.hueverianietoclientes.base.BaseComponent
import com.example.hueverianietoclientes.databinding.ComponentButtonBinding
import com.example.hueverianietoclientes.utils.Conversions

open class HNButton : ConstraintLayout, BaseComponent {

    private var binding: ComponentButtonBinding = ComponentButtonBinding.inflate(
        LayoutInflater.from(this.context)
    )

    constructor(context: Context) : super(context) {
        this.addView(
            this.binding.root,
            LayoutParams.MATCH_PARENT,
            resources.getDimensionPixelSize(R.dimen.size64)
        )
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.addView(
            this.binding.root,
            LayoutParams.MATCH_PARENT,
            resources.getDimensionPixelSize(R.dimen.size64)
        )
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        this.addView(
            this.binding.root,
            LayoutParams.MATCH_PARENT,
            resources.getDimensionPixelSize(R.dimen.size64)
        )
    }

    private fun initAttrs(attrs: AttributeSet?) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.HNButton
        )
        this.initBoolAttrs(typedArray)
        this.initStringAttrs(typedArray)
        typedArray.recycle()
    }

    private fun initBoolAttrs(typedArray: TypedArray) {
        typedArray.getBoolean(R.styleable.HNButton_isButtonEnabled, true).let {
            this.isEnabled = false
        }
    }

    private fun initStringAttrs(typedArray: TypedArray) {
        typedArray.getString(R.styleable.HNButton_text)?.let {
            this.setText(it)
        }
    }

    fun setText(text: String) {
        this.binding.buttonText.text = text
    }

    fun setButtonTextSize(size: Float) {
        this.binding.buttonText.textSize
    }

    fun setTextBold(isBold: Boolean) {
        this.binding.buttonBackground.isEnabled = false
        if (isBold) {
            this.binding.buttonText.setTypeface(null, Typeface.BOLD)
        }
    }

    fun setTextColor(color: Int) {
        this.binding.buttonText.setTextColor(color)
    }

    fun setHorizontalTextMargin(margin: Int) {
        val param = this.binding.buttonText.layoutParams as MarginLayoutParams
        param.setMargins(margin, 0, margin, 0)
        this.binding.buttonText.layoutParams = param
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        this.binding.buttonBackground.isEnabled = enabled
        this.binding.buttonText.isEnabled = enabled
    }

    fun setSize(width: Int?, height: Int?) {
        this.binding.buttonBackground.layoutParams =
            LayoutParams(
                if (width == null) LayoutParams.MATCH_PARENT else Conversions.convertPxToDp(width),
                if (height == null) Conversions.convertPxToDp(R.dimen.size64) else Conversions.convertPxToDp(
                    height
                )
            )
    }

    override fun getComponentContext(): Context {
        return this.context
    }

    override fun getView(): View {
        return this
    }

}
