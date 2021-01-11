package com.mua.mlauncher;

import android.view.View;

public class Test implements  ApplicationClickListener{
    Test(){
    }

    void test(){
        new ApplicationListAdapter(this);

    }

    @Override
    public void onApplicationClick(ApplicationInfo applicationInfo) {

    }
}
