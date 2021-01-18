package ru.skillbranch.avito_intern_test.model

import android.os.Parcel
import android.os.Parcelable

class ListOfNumber(val itemNumber: String?) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(itemNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListOfNumber> {
        override fun createFromParcel(parcel: Parcel): ListOfNumber {
            return ListOfNumber(parcel)
        }

        override fun newArray(size: Int): Array<ListOfNumber?> {
            return arrayOfNulls(size)
        }
    }
}