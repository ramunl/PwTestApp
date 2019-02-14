package ru.pwtest.pwapp.model

import android.os.Parcel
import android.os.Parcelable

data class UserViewModel(
    val id: Int,
    val name: String,
    val balance: Int?,
    val email: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(name)
        writeValue(balance)
        writeString(email)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<UserViewModel> = object : Parcelable.Creator<UserViewModel> {
            override fun createFromParcel(source: Parcel): UserViewModel = UserViewModel(source)
            override fun newArray(size: Int): Array<UserViewModel?> = arrayOfNulls(size)
        }
    }
}