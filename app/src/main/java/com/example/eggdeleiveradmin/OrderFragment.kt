package com.example.eggdeleiveradmin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eggdeleiveradmin.adapter.Data
import com.example.eggdeleiveradmin.adapter.DataAdapter
import com.example.eggdeleiveradmin.common.Constant
import com.example.eggdeleiveradmin.common.Constant.ORDER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.fragment_order.*

class OrderFragment : Fragment() {
    private val list = mutableListOf<Data>()
    private lateinit var adapter: DataAdapter
    private lateinit var mAuth: FirebaseAuth
    private val databaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        adapter = DataAdapter(list)
        rv_order.setHasFixedSize(true)
        rv_order.adapter = adapter

        databaseReference.child(ORDER)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (i in snapshot.children) {
                            if (i.exists()) {
                                val myName = i.child(Constant.name).value
                                val myAddressName = i.child(Constant.addressName).value
                                val myDate = i.child("date").getValue<Long>()
                                val myPhone = i.child(Constant.phone).value
                                val myCount = i.child(Constant.count).getValue<Int>()

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
                                        "yoq"
                                    )
                                    list.add(myData)
                                }
                            }
                        }
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}
