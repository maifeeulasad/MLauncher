package com.mua.mlauncher

import android.graphics.drawable.Drawable

class ApplicationInfo(applicationName: String, applicationDrawable: Drawable) {
    var applicationName: String = applicationName
        get() = field
        set(value) { field = value }
    var applicationDrawable: Drawable = applicationDrawable
        get() = field
        set(value) { field = value }
}