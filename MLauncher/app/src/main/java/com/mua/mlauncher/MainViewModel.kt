package com.mua.mlauncher

import android.app.Application
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val applications = MutableLiveData(mutableListOf<ApplicationInfo>())
    val queryString = ObservableField<String>("")

    init {
        listAllApplication(application.packageManager)
    }

    fun getApplications(): LiveData<MutableList<ApplicationInfo>> {
        return applications
    }

    private fun listAllApplication(packageManager: PackageManager) {
        val applicationList: MutableList<ApplicationInfo> = mutableListOf()
        val appList = getAllApplications(packageManager)
        for (i in appList.indices) {
            val packageInfo = appList[i]
            val applicationName =
                packageInfo.applicationInfo.loadLabel(packageManager).toString()
            val applicationDrawable = packageInfo.applicationInfo.loadIcon(packageManager)
            val applicationPackage = packageInfo.applicationInfo.packageName

            if(isUsableApplication(packageManager,applicationPackage)){
                applicationList.add(
                    ApplicationInfo(
                        applicationName,
                        applicationPackage,
                        applicationDrawable
                    )
                )
            }
        }
        applications.postValue(applicationList)
    }

    private fun getAllApplications(packageManager: PackageManager) : List<PackageInfo> {
        return packageManager.getInstalledPackages(0)
    }

    private fun isUsableApplication(packageManager: PackageManager, packageName: String) : Boolean{
        return try {
            packageManager.getLaunchIntentForPackage(packageName) ?: return false
            true
        } catch (e: Exception) {
            false
        }
    }

}