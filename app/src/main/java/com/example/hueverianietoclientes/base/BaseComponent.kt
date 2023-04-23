package com.example.hueverianietoclientes.base

import android.content.Context
import android.view.View

interface BaseComponent {
    fun getComponentContext(): Context
    fun getView(): View
}