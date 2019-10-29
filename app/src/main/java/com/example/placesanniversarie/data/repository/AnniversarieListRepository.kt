package com.example.Anniversariesanniversarie.data.repository

import com.example.placesanniversarie.data.model.Anniversarie
import com.google.firebase.database.*

class AnniversarieListRepository {
    protected lateinit var databaseReference: DatabaseReference
    protected  var firebaseCallback: FirebaseDatabaseRepositoryCallback<Anniversarie>?=null
    private var listener: ChildEventListener? = null
    private var anniversarieList = ArrayList<Anniversarie>()

    constructor () {
        databaseReference = FirebaseDatabase.getInstance().getReference("anniversaire")
    }

    fun getFromId(id: String): ArrayList<Anniversarie> {

        databaseReference=FirebaseDatabase.getInstance().getReference("anniversaire").child(id)
        listener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                val td = dataSnapshot.getValue<Anniversarie>(Anniversarie::class.java!!)
                if (td != null) {
                    td.id = (dataSnapshot.key)
                    anniversarieList.add(td)
                }
                firebaseCallback?.onSuccess(anniversarieList)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        databaseReference.addChildEventListener(listener!!)
        return anniversarieList
    }

    fun addListener(firebaseCallback: FirebaseDatabaseRepositoryCallback<Anniversarie>) {
        this.firebaseCallback = firebaseCallback
    }

    fun removeListener() {
        databaseReference.removeEventListener(listener!!)
    }

    interface FirebaseDatabaseRepositoryCallback<T> {
        fun onSuccess(result: List<T>)

        fun onError(e: DatabaseError)
    }
}