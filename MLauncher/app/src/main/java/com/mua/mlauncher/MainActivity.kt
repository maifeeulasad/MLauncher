package com.mua.mlauncher

import android.content.pm.ApplicationInfo
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val allApplications = listAllApplication()
        for(app in allApplications){
            Log.d("d--mua",app.applicationName)
        }
    }

    private fun listAllApplication(): MutableList<AppInfo> {
        val apps : MutableList<AppInfo> = ArrayList()
        val packageManager = packageManager;
        val appList = packageManager!!.getInstalledPackages(0)
        for (i in appList.indices) {
            val packageInfo = appList[i]
            if (packageInfo!!.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                val applicationName = packageInfo.applicationInfo.loadLabel(packageManager).toString()
                val applicationDrawable = packageInfo.applicationInfo.loadIcon(packageManager)

                apps.add(AppInfo(applicationName,applicationDrawable))
            }
        }
        return apps
    }

}

class AppInfo(applicationName: String, applicationDrawable: Drawable) {
    var applicationName: String = applicationName
        get() = field
        set(value) { field = value }
    var applicationDrawable: Drawable = applicationDrawable
        get() = field
        set(value) { field = value }
}