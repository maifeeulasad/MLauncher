package com.mua.mlauncher

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable

class ApplicationInfo(
    var applicationName: String,
    var applicationPackage: String,
    var applicationDrawable: Drawable,
    var isContent: Boolean,
    var header: String
) {
    constructor(header: String)
            : this(header, "", ColorDrawable(Color.TRANSPARENT), false, header)

    constructor(applicationName: String, applicationPackage: String, applicationDrawable: Drawable)
            : this(applicationName, applicationPackage, applicationDrawable, true, "")
}
