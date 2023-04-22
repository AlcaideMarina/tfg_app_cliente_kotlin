package com.example.hueverianietoclientes.domain.model

import android.graphics.drawable.Drawable
import android.view.View.OnClickListener

data class GridItemModel(
    var label: String,
    var icon: Drawable,
    var onClickListener: OnClickListener
)
