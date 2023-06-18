package com.example.hueverianietoclientes.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun setUp()
    protected abstract fun configureUI()
    protected abstract fun setListeners()

    protected abstract fun setObservers()

    protected abstract fun updateUI(state: BaseState)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(
            this.javaClass.simpleName,
            "Calling activity onCreate(): " + this.javaClass.simpleName
        )
        setUp()
        configureUI()
        setListeners()
        setObservers()
    }

    override fun onStart() {
        super.onStart()
        Log.d(this.javaClass.simpleName, "Calling activity onStart(): " + this.javaClass.simpleName)
    }

    override fun onResume() {
        super.onResume()
        Log.d(
            this.javaClass.simpleName,
            "Calling activity onResume(): " + this.javaClass.simpleName
        )
    }

    override fun onPause() {
        super.onPause()
        Log.d(this.javaClass.simpleName, "Calling activity onPause(): " + this.javaClass.simpleName)
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(
            this.javaClass.simpleName,
            "Calling activity onRestart(): " + this.javaClass.simpleName
        )
    }

    override fun onStop() {
        super.onStop()
        Log.d(this.javaClass.simpleName, "Calling activity onStop(): " + this.javaClass.simpleName)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(
            this.javaClass.simpleName,
            "Calling activity onDestroy(): " + this.javaClass.simpleName
        )
    }

    fun View.hideSoftInput() {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

}
