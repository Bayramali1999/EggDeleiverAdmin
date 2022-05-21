package com.example.eggdeleiveradmin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

//        main_tabs_pager.adapter  = TabAccessAdapter()
        main_tabs.setupWithViewPager(main_tabs_pager)


    }
}