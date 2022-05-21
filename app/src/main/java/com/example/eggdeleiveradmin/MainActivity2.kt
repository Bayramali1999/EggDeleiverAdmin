package com.example.eggdeleiveradmin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import com.example.eggdeleiveradmin.adapter.Data
import com.example.eggdeleiveradmin.listener.ItemClickListener
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity(), ItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        restAll()

        supportFragmentManager.beginTransaction().replace(R.id.frame_container, OrderFragment())
            .commit()
        container_order.setBackgroundColor(resources.getColor(R.color.main_color))
        container_order.setPadding(7)

        openView()
    }

    private fun restAll() {
        container_history.setBackgroundColor(resources.getColor(R.color.white))
        container_order.setBackgroundColor(resources.getColor(R.color.white))

        container_history.setPadding(15)
        container_order.setPadding(15)


    }

    private fun openView() {
        container_history.setOnClickListener {
            restAll()
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, HistoryFragment()).commit()
            container_history.setBackgroundColor(resources.getColor(R.color.main_color))
            container_history.setPadding(7)
        }

        container_order.setOnClickListener {
            restAll()
            supportFragmentManager.beginTransaction().replace(R.id.frame_container, OrderFragment())
                .commit()
            container_order.setBackgroundColor(resources.getColor(R.color.main_color))
            container_order.setPadding(7)

        }
    }

    override fun onItemClicked(data: Data) {

    }


}