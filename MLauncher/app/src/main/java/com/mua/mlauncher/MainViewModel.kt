package com.mua.mlauncher

import android.app.Application
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.ViewConfiguration
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val applications = MutableLiveData(mutableListOf<ApplicationInfo>())
    val queryString = ObservableField<String>("")
    private val _navBarHeight = MutableLiveData(0)
    val navBarHeight
            : LiveData<Int>
            = _navBarHeight

    init {
        _navBarHeight.postValue(getNavBarHeight(application.applicationContext))
        viewModelScope.launch(Dispatchers.IO) {
            listAllApplication(application.packageManager)
        }
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

    private fun getNavBarHeight(c: Context): Int {
        val hasMenuKey = ViewConfiguration.get(c).hasPermanentMenuKey()
        val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
        if (!hasMenuKey && !hasBackKey) {
            val resources: Resources = c.resources
            val orientation: Int = resources.configuration.orientation
            val resourceId: Int
            resourceId = if (isTablet(c)) {
                resources.getIdentifier(
                    if (orientation == Configuration.ORIENTATION_PORTRAIT)
                        "navigation_bar_height"
                    else
                        "navigation_bar_height_landscape",
                    "dimen",
                    "android"
                )
            } else {
                resources.getIdentifier(
                    if (orientation == Configuration.ORIENTATION_PORTRAIT)
                        "navigation_bar_height"
                    else
                        "navigation_bar_width",
                    "dimen",
                    "android"
                )
            }
            if (resourceId > 0) {
                return resources.getDimensionPixelSize(resourceId)
            }
        }
        return 0
    }


    private fun isTablet(c: Context): Boolean {
        return ((c.resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE)
    }


}