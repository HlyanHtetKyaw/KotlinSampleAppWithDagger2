package com.example.testforcodigo.ui.main

import android.os.Bundle
import com.example.testforcodigo.R
import com.example.testforcodigo.base.BaseActivity
import com.example.testforcodigo.ui.list.WonderListFragment

class MainActivity : BaseActivity() {
    override fun layoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(
                R.id.id_container,
                WonderListFragment()
            ).commit()
        }
    }
}
