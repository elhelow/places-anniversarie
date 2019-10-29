package com.example.placesanniversarie.viewmodel


import android.app.Activity
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.placesanniversarie.data.model.CallState
import com.example.placesanniversarie.data.model.Place
import com.example.placesanniversarie.data.model.UploadeImage
import com.example.placesanniversarie.data.repository.ImageRepository
import com.example.placesanniversarie.data.repository.UpdatePlaceRepository
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseError

class UpdatePlaceModel : ViewModel() {

    private var callStatus: MutableLiveData<CallState>? = null
    private var uploadeImage: MutableLiveData<UploadeImage>? = null
    private val repository = UpdatePlaceRepository()
    private val imageRepository = ImageRepository()

    val callStatusL: LiveData<CallState>
        get() {
            if (callStatus == null) {
                callStatus = MutableLiveData()
                intiatie()
            }
            return callStatus!!
        }
    val uploadeImageL: LiveData<UploadeImage>
        get() {
            if (uploadeImage == null) {
                uploadeImage = MutableLiveData()
            }
            return uploadeImage!!
        }

    override fun onCleared() {
        repository.removeListener()
    }

    private fun intiatie() {
        repository.addListener(object : UpdatePlaceRepository.FirebaseDatabaseRepositoryCallback {

            override fun onSuccess() {
                var c = CallState()
                c.msg = ""
                c.state = CallState.CallState.SUCCESS
                callStatus!!.postValue(c)
            }

            override fun onError(e: DatabaseError) {
                var c = CallState()
                c.msg = e.message
                c.state = CallState.CallState.SUCCESS
                callStatus!!.postValue(c)
            }
        })
    }

    fun update(place: Place) {
        if (place!!.id == null) {
            repository.addPlace(place)
        } else {
            repository.updatePlace(place)

        }
    }

    var uImage = UploadeImage()
    fun uploadImage(a: Activity, imageUri: Uri?) {

        uImage = UploadeImage()
        var ref = imageRepository.addImage(imageUri)
        ref.getDownloadUrl().addOnSuccessListener(a,
            OnSuccessListener<Uri> { uri ->
                val url = uri.toString()
                uImage.imageUrl = url
                uploadeImage!!.postValue(uImage)
            })
        ref.putFile(imageUri!!)
            .addOnSuccessListener({ taskSnapshot ->
                uImage.imageName = taskSnapshot.storage.path
                uploadeImage!!.postValue(uImage)
            })

    }
}
