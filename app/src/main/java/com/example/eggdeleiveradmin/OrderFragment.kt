package com.example.eggdeleiveradmin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.eggdeleiveradmin.adapter.Data
import com.example.eggdeleiveradmin.adapter.DataAdapter
import com.example.eggdeleiveradmin.common.Constant
import com.example.eggdeleiveradmin.common.Constant.ORDER
import com.example.eggdeleiveradmin.listener.ItemClickListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.deliver_item.view.*
import kotlinx.android.synthetic.main.fragment_order.*

class OrderFragment : Fragment(), ItemClickListener {
    private val list = mutableListOf<Data>()
    private lateinit var adapter: DataAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var dialogDeliver: AlertDialog
    private lateinit var progressDialog: AlertDialog
    private val databaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressDialog = AlertDialog.Builder(requireContext())
            .setView(R.layout.dialog_view)
            .create()
        progressDialog.show()

        mAuth = FirebaseAuth.getInstance()
        adapter = DataAdapter(list, this)
        rv_order.setHasFixedSize(true)
        rv_order.adapter = adapter

        databaseReference.child(ORDER)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    progressDialog.dismiss()
                    if (snapshot.exists()) {

                        for (i in snapshot.children) {
                            if (i.exists()) {
                                val myName = i.child(Constant.name).value
                                val myAddressName = i.child(Constant.addressName).value
                                val myDate = i.child("date").getValue<Long>()
                                val myPhone = i.child(Constant.phone).value
                                val myCount = i.child(Constant.count).getValue<Int>()
                                val lat = i.child("lat").getValue<Double>()
                                val long = i.child("long").getValue<Double>()
                                if (myAddressName != null &&
                                    myName != null &&
                                    myPhone != null
                                ) {
                                    val myData = Data(
                                        myAddressName as String,
                                        myName as String,
                                        myDate,
                                        myPhone as String,
                                        myCount,
                                        "yoq",
                                        "yoq",
                                        lat!!,
                                        long!!
                                    )
                                    list.add(myData)
                                }
                            }
                        }
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    progressDialog.dismiss()
                }
            })
    }

    override fun onItemClicked(data: Data) {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.deliver_item, null)

        view.deliver_name.text = "Name: ${data.name}"
        view.deliver_count.text = "Soni: ${data.count}"
        view.deliver_phone.text = "Telephone: ${data.phone}"
        view.deliver_pos.text = "Manzil: ${data.addressName}"
        view.deliver_data.text = "Sana: ${data.date}"

        dialogDeliver = AlertDialog.Builder(requireContext()).setView(view).create()
        dialogDeliver.show()

        view.deliver_phone.setOnClickListener {

        }

        view.deliver.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=${data.lat},${data.long}")
            )
            startActivity(intent)
        }

    }
}
