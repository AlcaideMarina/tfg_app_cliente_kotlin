package com.example.hueverianietoclientes.base.core

open class Event<out T>(private val content: T) {

    var controlled = false
        private set

    fun getControlled(): T? {
        return if (controlled) null
        else {
            controlled = true
            content
        }
    }

}
