package com.mua.mlauncher

import android.R.attr.name
import android.R.attr.password
import android.content.Intent
import android.content.pm.PackageManager
import android.opengl.ETC1.isValid
import android.os.Bundle
import android.util.Log
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
                Log.d("d--mua", viewModel.queryString.get() + "")
            }
        }

        viewModel.queryString.addOnPropertyChangedCallback(callback)
    }

    private fun initList() {
        val appUsageListAdapter = ApplicationListAdapter(this)
        val appUsageRecyclerView: RecyclerView = findViewById(R.id.rv_apps)
        appUsageRecyclerView.adapter = appUsageListAdapter
        appUsageRecyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.getApplications().observe(this, Observer { applicationList ->
            appUsageListAdapter.setApplicationList(applicationList)
        })
    }


    override fun onApplicationClick(applicationInfo: ApplicationInfo) {
        startApplication(applicationInfo.applicationPackage)
    }

    private fun startApplication(applicationPackage: String) {
        val intent: Intent
        val pm: PackageManager = packageManager

        intent = pm.getLaunchIntentForPackage(applicationPackage)!!

        startActivity(intent)
    }

}
