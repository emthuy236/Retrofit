package com.example.retrofit

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Sinhvien() :Parcelable {
    @SerializedName("Id")
    @Expose
    var id: String? = null
    @SerializedName("User")
    @Expose
    var user: String? = null
    @SerializedName("Password")
    @Expose
    var password: String? = null
    @SerializedName("Hinhanh")
    @Expose
    var hinhanh: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        user = parcel.readString()
        password = parcel.readString()
        hinhanh = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(user)
        parcel.writeString(password)
        parcel.writeString(hinhanh)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Sinhvien> {
        override fun createFromParcel(parcel: Parcel): Sinhvien {
            return Sinhvien(parcel)
        }

        override fun newArray(size: Int): Array<Sinhvien?> {
            return arrayOfNulls(size)
        }
    }

}