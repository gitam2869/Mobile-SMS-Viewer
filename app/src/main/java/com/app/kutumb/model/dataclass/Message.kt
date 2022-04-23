package com.app.kutumb.model.dataclass

import android.os.Parcel
import android.os.Parcelable

data class MessageData(
    val threadId: Int,
    val number: String,
    val message: String,
    val type: Int,
    val time: Long
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(threadId)
        parcel.writeString(number)
        parcel.writeString(message)
        parcel.writeInt(type)
        parcel.writeLong(time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MessageData> {
        override fun createFromParcel(parcel: Parcel): MessageData {
            return MessageData(parcel)
        }

        override fun newArray(size: Int): Array<MessageData?> {
            return arrayOfNulls(size)
        }
    }
}