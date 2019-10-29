package com.example.placesanniversarie.data.model

import android.os.Parcel
import android.os.Parcelable

class Place() : Parcelable {
    var anniversarieList = ArrayList<Anniversarie>()
    var id: String? = null
    var name = ""
    var lat = 0.0
    var lng = 0.0
    var imageUrl: String? = null
    var imageName: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        name = parcel.readString()
        lat = parcel.readDouble()
        lng = parcel.readDouble()
        imageUrl = parcel.readString()
        imageName = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeDouble(lat)
        parcel.writeDouble(lng)
        parcel.writeString(imageUrl)
        parcel.writeString(imageName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Place> {
        override fun createFromParcel(parcel: Parcel): Place {
            return Place(parcel)
        }

        override fun newArray(size: Int): Array<Place?> {
            return arrayOfNulls(size)
        }
    }
}
