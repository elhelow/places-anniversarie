package com.example.placesanniversarie.data.repository

import android.net.Uri
import com.google.firebase.database.DatabaseError
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ImageRepository {
    protected lateinit var databaseReference: StorageReference
    protected lateinit var firebaseCallback: FirebaseDatabaseRepositoryCallback
    private lateinit var mFirebaseStorageR: StorageReference

    constructor () {
        val mFirebaseStorage = FirebaseStorage.getInstance()
        mFirebaseStorageR = mFirebaseStorage.getReference().child("places-anniversarie")

    }

    fun addImage(imageUri: Uri?): StorageReference {
        databaseReference = mFirebaseStorageR.child(imageUri!!.lastPathSegment)
        return databaseReference
    }


    fun addListener(firebaseCallback: FirebaseDatabaseRepositoryCallback) {
        this.firebaseCallback = firebaseCallback


    }

    fun removeListener() {

    }

    interface FirebaseDatabaseRepositoryCallback {
        fun onSuccess()

        fun onError(e: DatabaseError)
    }
}