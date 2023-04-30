package com.example.hueverianietoclientes.domain.model

import android.view.View.OnClickListener
import com.google.firebase.Timestamp

class BillingContainerModel (
    var initDate: Timestamp,
    var endDate: Timestamp,
    //var onClickListener: OnClickListener?, // TODO: Esto va a ir en otro modelo nuevo -> item
    var billingModel: BillingModel?
)
