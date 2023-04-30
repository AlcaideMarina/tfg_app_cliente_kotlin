package com.example.hueverianietoclientes.domain.model

import android.view.View.OnClickListener
import com.google.firebase.Timestamp

class BillingContainerModel (
    var initDate: Timestamp,
    var finishDate: Timestamp,
    var onClickListener: OnClickListener
)
