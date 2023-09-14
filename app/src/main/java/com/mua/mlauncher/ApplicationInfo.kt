package com.mua.mlauncher

import android.graphics.drawable.Drawable

class ApplicationInfo(
    var applicationName: String,
    var applicationPackage: String,
    var applicationDrawable: Drawable
) {
    var isContent: Boolean = true
}