package com.mua.mlauncher

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity(), ApplicationClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val appUsageListAdapter = ApplicationListAdapter(this)
        val appUsageRecyclerView: RecyclerView = findViewById(R.id.rv_apps)
        appUsageRecyclerView.adapter = appUsageListAdapter
        appUsageRecyclerView.layoutManager = LinearLayoutManager(this)

        val allApplications = listAllApplication()

        appUsageListAdapter.setApplicationList(allApplications)
    }

    private fun listAllApplication(): MutableList<com.mua.mlauncher.ApplicationInfo> {
        val applications: MutableList<com.mua.mlauncher.ApplicationInfo> = ArrayList()
        val packageManager = packageManager
        val appList = packageManager!!.getInstalledPackages(0)
        for (i in appList.indices) {
            val packageInfo = appList[i]
            if (packageInfo!!.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                val applicationName =
                    packageInfo.applicationInfo.loadLabel(packageManager).toString()
                val applicationDrawable = packageInfo.applicationInfo.loadIcon(packageManager)
                val applicationPackage = packageInfo.applicationInfo.packageName

                applications.add(
                    ApplicationInfo(
                        applicationName,
                        applicationPackage,
                        applicationDrawable
                    )
                )
            }
        }
        return applications
    }

    override fun onApplicationClick(applicationInfo: com.mua.mlauncher.ApplicationInfo) {
        startApplication(applicationInfo.applicationPackage)
    }

    private fun startApplication(applicationPackage: String) {
        val intent: Intent
        val pm: PackageManager = packageManager

        intent = pm.getLaunchIntentForPackage(applicationPackage)!!

        startActivity(intent)
    }

}
