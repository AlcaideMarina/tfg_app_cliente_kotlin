package com.example.hueverianietoclientes.domain.model

import com.google.firebase.Timestamp

class BillingContainerModel(
    var initDate: Timestamp,
    var endDate: Timestamp,
    var billingModel: BillingModel?
)
