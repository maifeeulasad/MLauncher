package com.mua.mlauncher

import android.graphics.drawable.Drawable

class ApplicationInfo(applicationName: String, applicationPackage: String, applicationDrawable: Drawable) {
    var applicationName: String = applicationName
        get() = field
        set(value) { field = value }
    var applicationPackage: String = applicationPackage
        get() = field
        set(value) { field = value }
    var applicationDrawable: Drawable = applicationDrawable
        get() = field
        set(value) { field = value }
    var isContent: Boolean = true
        get() = field
        set(value) { field = value }
}