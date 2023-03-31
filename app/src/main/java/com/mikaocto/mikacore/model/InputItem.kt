package com.mikaocto.mikacore.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "InputItem")
data class InputItem(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "itemName")val itemName:String,
    @ColumnInfo(name = "employeeName") val employeeName: String,
    @ColumnInfo(name = "barcodeValue") val barcodeValue: String
)
