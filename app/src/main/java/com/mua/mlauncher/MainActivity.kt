package com.mua.mlauncher

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mua.mlauncher.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), ApplicationClickListener {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var applicationListAdapter: ApplicationListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mBinding.main = viewModel
        mBinding.lifecycleOwner = this

        init()
    }

    private fun init() {
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        initSearch()
        initList()
    }

    private fun initSearch() {
        val callback: OnPropertyChangedCallback = object : OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                applicationListAdapter.filter.filter(viewModel.queryString.get())
            }
        }

        viewModel.queryString.addOnPropertyChangedCallback(callback)
    }

    private fun initList() {
        applicationListAdapter = ApplicationListAdapter(this)
        val appUsageRecyclerView: RecyclerView = findViewById(R.id.rv_apps)
        appUsageRecyclerView.adapter = applicationListAdapter
        appUsageRecyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.getApplications().observe(this, Observer { applicationList ->
            applicationListAdapter.setApplicationList(applicationList)
        })
    }


    override fun onApplicationClick(applicationInfo: ApplicationInfo) {
        startApplication(applicationInfo.applicationPackage)
    }

    override fun onApplicationInfoClick(applicationInfo: ApplicationInfo) {
        openApplicationInfo(applicationInfo);
    }

    private fun startApplication(applicationPackage: String) {
        val intent: Intent
        val pm: PackageManager = packageManager
        intent = pm.getLaunchIntentForPackage(applicationPackage)!!

        startActivity(intent)
    }

    private fun openApplicationInfo(applicationInfo: ApplicationInfo){
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:${applicationInfo.applicationPackage}")

        startActivity(intent)
    }

}
