package com.example.eggdeleiveradmin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eggdeleiveradmin.adapter.Data
import com.example.eggdeleiveradmin.adapter.DataAdapter
import com.example.eggdeleiveradmin.common.Constant
import com.example.eggdeleiveradmin.common.Constant.HISTORY
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment() {

    private val list = mutableListOf<Data>()
    private lateinit var adapter: DataAdapter
    private val databaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DataAdapter(list)
        rv_history.setHasFixedSize(true)
        rv_history.adapter = adapter


        databaseReference.child(HISTORY)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (first in snapshot.children) {
                            if (first.exists()) {
                                first.ref.addValueEventListener(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        if (snapshot.exists()) {
                                            for (item in snapshot.children) {
                                                val myName = item.child(Constant.name).value
                                                val myAddressName =
                                                    item.child(Constant.addressName).value
                                                val myDate = item.child("date").getValue<Long>()
                                                val myPhone = item.child(Constant.phone).value
                                                val myCount =
                                                    item.child(Constant.count).getValue<Int>()

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
                                                        "xa",
                                                        "xa"
                                                    )
                                                    list.add(myData)
                                                    Log.d("TAG", "onDataChange: $myData")
                                                }

                                            }
                                        }
                                        adapter.notifyDataSetChanged()
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                    }

                                })
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}

