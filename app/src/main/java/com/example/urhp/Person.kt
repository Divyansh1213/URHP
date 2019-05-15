package com.example.urhp

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "table_person")
data class Person(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "FirstName") @SerializedName("first_name") var first_name: String,
    @ColumnInfo(name = "LastName") @SerializedName("last_name") var last_name: String,
    @ColumnInfo(name = "avatar") var avatar: String
)