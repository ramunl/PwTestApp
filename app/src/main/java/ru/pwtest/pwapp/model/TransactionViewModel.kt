package ru.pwtest.pwapp.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class TransactionViewModel(
        val id: Int,
        val date: Date,
        val username: String,
        val amount: Int,
        val balance: Int
) : Parcelable {
        constructor(source: Parcel) : this(
                source.readInt(),
                source.readSerializable() as Date,
                source.readString(),
                source.readInt(),
                source.readInt()
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
                writeInt(id)
                writeSerializable(date)
                writeString(username)
                writeInt(amount)
                writeInt(balance)
        }

        companion object {
                @JvmField
                val CREATOR: Parcelable.Creator<TransactionViewModel> =
                        object : Parcelable.Creator<TransactionViewModel> {
                                override fun createFromParcel(source: Parcel): TransactionViewModel =
                                        TransactionViewModel(source)

                                override fun newArray(size: Int): Array<TransactionViewModel?> = arrayOfNulls(size)
                        }
        }
}