package com.example.hueverianietoclientes.domain.model

import android.view.View
import com.example.hueverianietoclientes.base.BaseModel

data class ModalDialogModel(
    var title: String,
    var text: String,
    var leftButtonText: String,
    var rightButtonText: String?,
    var leftButtonListener: View.OnClickListener,
    var rightButtonListener: View.OnClickListener?,
    var cancelledOnTouchOutside: Boolean
) : BaseModel
