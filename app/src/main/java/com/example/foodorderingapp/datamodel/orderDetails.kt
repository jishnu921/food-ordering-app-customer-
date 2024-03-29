package com.example.foodorderingapp.datamodel

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

class orderDetails ():Parcelable{
    var userUid:String?=null
    var userName:String?=null
    var address:String?=null
    var totalPrice:String?=null
    var phoneNumber:String?=null
    var itemPushKey:String?=null
    var foodNames:MutableList<String>?=null
    var foodImage:MutableList<String>?=null
    var foodPrice:MutableList<String>?=null
    var foodQuantity:MutableList<String>?=null
    var orderAccepted:Boolean = false
    var orderRecived:Boolean = false
    var currentTime : Long = 0

    constructor(parcel: Parcel) : this() {
        userUid = parcel.readString()
        userName = parcel.readString()
        address = parcel.readString()
        totalPrice = parcel.readString()
        phoneNumber = parcel.readString()
        itemPushKey = parcel.readString()
        orderAccepted = parcel.readByte() != 0.toByte()
        orderRecived = parcel.readByte() != 0.toByte()
        currentTime = parcel.readLong()
    }

    constructor(
        userId: String,
        name: String,
        address: String,
        totalAmount: String,
        phone: String,
        itemPushKey: String?,
        foodName: ArrayList<String>,
        foodImage: ArrayList<String>,
        foodPrice: ArrayList<String>,
        foodItemQuanity: ArrayList<Int>,
        b: Boolean,
        b1: Boolean,
        time: Long
    ) : this()

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userUid)
        parcel.writeString(userName)
        parcel.writeString(address)
        parcel.writeString(totalPrice)
        parcel.writeString(phoneNumber)
        parcel.writeString(itemPushKey)
        parcel.writeByte(if (orderAccepted) 1 else 0)
        parcel.writeByte(if (orderRecived) 1 else 0)
        parcel.writeLong(currentTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<orderDetails> {
        override fun createFromParcel(parcel: Parcel): orderDetails {
            return orderDetails(parcel)
        }

        override fun newArray(size: Int): Array<orderDetails?> {
            return arrayOfNulls(size)
        }
    }
}