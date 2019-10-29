package com.example.placesanniversarie.data.repository

import com.example.placesanniversarie.data.model.Place
import com.google.firebase.database.*

class UpdatePlaceRepository {
    protected lateinit var databaseReference: DatabaseReference
    protected lateinit var firebaseCallback: FirebaseDatabaseRepositoryCallback
    private var listener: ValueEventListener? = null

    constructor () {
        databaseReference = FirebaseDatabase.getInstance().getReference("places")
    }

    fun updatePlace(place: Place) {
        databaseReference.child(place!!.id!!).setValue(place)

    }

    fun addPlace(place: Place) {
        databaseReference.push().setValue(place)
    }

    fun addListener(firebaseCallback: FirebaseDatabaseRepositoryCallback) {
        this.firebaseCallback = firebaseCallback

        listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                firebaseCallback.onSuccess()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                firebaseCallback.onError(databaseError)
            }
        }
        databaseReference.addValueEventListener(listener!!)
    }

    fun removeListener() {
        if (listener != null)
            databaseReference.removeEventListener(listener!!)
    }

    interface FirebaseDatabaseRepositoryCallback {
        fun onSuccess()

        fun onError(e: DatabaseError)
    }
}