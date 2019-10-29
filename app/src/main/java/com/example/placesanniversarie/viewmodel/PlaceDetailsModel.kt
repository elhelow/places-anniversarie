package com.example.placesanniversarie.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.Anniversariesanniversarie.data.repository.AnniversarieListRepository
import com.example.placesanniversarie.data.model.Anniversarie
import com.example.placesanniversarie.data.model.CallState
import com.google.firebase.database.DatabaseError

class PlaceDetailsModel : ViewModel() {

    private var callStatus: MutableLiveData<CallState>? = null
    private var anniList: MutableLiveData<List<Anniversarie>>? = null

    var repository = AnniversarieListRepository()
    val anniListL: LiveData<List<Anniversarie>>
        get() {
            if (anniList == null) {
                anniList = MutableLiveData()
                intiatie()
            }
            return anniList!!
        }
    val callStatusL: LiveData<CallState>
        get() {
            if (callStatus == null) {
                callStatus = MutableLiveData()
                intiatie()
            }
            return callStatus!!
        }

    override fun onCleared() {
        repository.removeListener()
    }

    private fun intiatie() {
        repository.addListener(object :
            AnniversarieListRepository.FirebaseDatabaseRepositoryCallback<Anniversarie> {

            override fun onSuccess(result: List<Anniversarie>) {
                anniList!!.value = result
            }

            override fun onError(e: DatabaseError) {
                anniList!!.value = null
            }
        })
    }

    fun getAnn(id: String) {

        repository.getFromId(id)
    }
}
