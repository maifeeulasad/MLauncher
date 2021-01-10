package com.mua.mlauncher

import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val appUsageListAdapter = ApplicationListAdapter()
        val appUsageRecyclerView: RecyclerView = findViewById(R.id.rv_apps)
        appUsageRecyclerView.adapter = appUsageListAdapter
        appUsageRecyclerView.layoutManager = LinearLayoutManager(this)

        val allApplications = listAllApplication()
        for(app in allApplications){
            Log.d("d--mua",app.applicationName)
        }

        appUsageListAdapter.setApplicationList(allApplications)
    }

    private fun listAllApplication(): MutableList<com.mua.mlauncher.ApplicationInfo> {
        val applications : MutableList<com.mua.mlauncher.ApplicationInfo> = ArrayList()
        val packageManager = packageManager;
        val appList = packageManager!!.getInstalledPackages(0)
        for (i in appList.indices) {
            val packageInfo = appList[i]
            if (packageInfo!!.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                val applicationName = packageInfo.applicationInfo.loadLabel(packageManager).toString()
                val applicationDrawable = packageInfo.applicationInfo.loadIcon(packageManager)

                applications.add(ApplicationInfo(applicationName,applicationDrawable))
            }
        }
        return applications
    }

}
