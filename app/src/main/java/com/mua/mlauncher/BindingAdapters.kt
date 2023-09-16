package com.mua.mlauncher

import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.databinding.BindingAdapter


object BindingAdapters {

    @JvmStatic
    @BindingAdapter("marginBottom")
    fun setMarginBottom(view: View, bottomMargin: Int) {
        view.layoutParams = (view.layoutParams as MarginLayoutParams).apply {
            this.bottomMargin = bottomMargin
        }
    }
}