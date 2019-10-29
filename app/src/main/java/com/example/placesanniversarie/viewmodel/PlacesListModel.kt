package com.example.placesanniversarie.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.placesanniversarie.data.model.Place
import com.example.placesanniversarie.data.repository.PlacesListRepository
import com.example.placesanniversarie.data.repository.PlacesListRepository.FirebaseDatabaseRepositoryCallback
import com.google.firebase.database.DatabaseError

class PlacesListModel : ViewModel() {



    private var Places: MutableLiveData<List<Place>>? = null
    private val repository = PlacesListRepository()

    val places: LiveData<List<Place>>
        get() {
            if (Places == null) {
                Places = MutableLiveData()
                loadPlaces()
            }
            return Places!!
        }

    override fun onCleared() {
        repository.removeListener()
    }

    private fun loadPlaces() {
        repository.addListener(object : FirebaseDatabaseRepositoryCallback<Place> {

            override fun onSuccess(result: List<Place>) {
                Places!!.value = result
            }

            override fun onError(e: DatabaseError) {
                Places!!.value = null
            }
        })
    }
}
