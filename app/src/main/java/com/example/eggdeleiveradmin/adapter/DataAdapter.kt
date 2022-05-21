package com.example.eggdeleiveradmin.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eggdeleiveradmin.R
import kotlinx.android.synthetic.main.item_view.view.*
import java.text.SimpleDateFormat

class DataAdapter(
    private val list: MutableList<Data>
) : RecyclerView.Adapter<DataAdapter.DataVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return DataVH(v)
    }

    override fun onBindViewHolder(holder: DataVH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount() = list.size

    class DataVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("ResourceAsColor")
        fun onBind(data: Data) {
            val sd = SimpleDateFormat("dd:MM:yyyy hh:mm")
            if (data.seen == "yoq") {
                itemView.item_container.setBackgroundColor(R.color.main_color)
            }
            itemView.order_name.text = "Nome ${data.name}"
            itemView.order_count.text = "Soni ${data.count}"
            itemView.order_time.text = "Sana ${sd.format(data.date)}"
        }

    }
}