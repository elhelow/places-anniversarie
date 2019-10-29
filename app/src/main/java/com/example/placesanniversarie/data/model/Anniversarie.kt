package com.example.placesanniversarie.data.model

import android.os.Parcel
import android.os.Parcelable

class Anniversarie() : Parcelable {
    var id: String? = null
    var name = ""
    var date = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        name = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Anniversarie> {
        override fun createFromParcel(parcel: Parcel): Anniversarie {
            return Anniversarie(parcel)
        }

        override fun newArray(size: Int): Array<Anniversarie?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "$name, "
    }

}
