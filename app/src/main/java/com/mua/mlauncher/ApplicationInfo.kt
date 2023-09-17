package com.mua.mlauncher

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable

class ApplicationInfo(
    var applicationName: String,
    var applicationPackage: String,
    applicationDrawableProvider: () -> Drawable,
    var isContent: Boolean,
    var header: String
) {
    val applicationDrawable: Drawable by lazy {
        applicationDrawableProvider()
    }

    constructor(header: String)
            : this(header, "", { ColorDrawable(Color.TRANSPARENT) }, false, header)

    constructor(
        applicationName: String,
        applicationPackage: String,
        applicationDrawableProvider: () -> Drawable
    )
            : this(applicationName, applicationPackage, applicationDrawableProvider, true, "")
}
