package com.example.placesanniversarie.data.repository

import com.example.placesanniversarie.data.model.Place
import com.google.firebase.database.*

class PlacesListRepository {
    protected lateinit var databaseReference: DatabaseReference
    protected lateinit var firebaseCallback: FirebaseDatabaseRepositoryCallback<Place>
    private var listener: ChildEventListener? = null
    private var places = ArrayList<Place>()

    constructor () {
        databaseReference = FirebaseDatabase.getInstance().getReference("places")
    }

    fun addListener(firebaseCallback: FirebaseDatabaseRepositoryCallback<Place>) {
        this.firebaseCallback = firebaseCallback
        listener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {

                val td = dataSnapshot.getValue<Place>(Place::class.java!!)
                if (td != null) {
                    td.id = (dataSnapshot.key)
                    places.add(td)
                }
                firebaseCallback.onSuccess(places)

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
    }

    fun removeListener() {
        databaseReference.removeEventListener(listener!!)
    }

    interface FirebaseDatabaseRepositoryCallback<T> {
        fun onSuccess(result: List<T>)

        fun onError(e: DatabaseError)
    }
}