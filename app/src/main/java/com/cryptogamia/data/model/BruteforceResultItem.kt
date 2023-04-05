package com.cryptogamia.data.model

import android.os.Parcel
import android.os.Parcelable

data class BruteforceResultItem (val key: Int, val message: String) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString()!!)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(key)
        parcel.writeString(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BruteforceResultItem> {
        override fun createFromParcel(parcel: Parcel): BruteforceResultItem {
            return BruteforceResultItem(parcel)
        }

        override fun newArray(size: Int): Array<BruteforceResultItem?> {
            return arrayOfNulls(size)
        }
    }
}
