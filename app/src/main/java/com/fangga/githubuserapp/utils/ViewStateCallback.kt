package com.fangga.githubuserapp.utils

import android.view.View

interface ViewStateCallback<T> {
    fun onLoading()
    fun onSuccess(data: T)
    fun onError(message: String?)

    val invisible: Int
        get() = View.INVISIBLE

    val visible: Int
        get() = View.VISIBLE
}