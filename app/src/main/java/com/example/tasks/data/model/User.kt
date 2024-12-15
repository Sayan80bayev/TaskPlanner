package com.example.tasks.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "users")
@Parcelize
class User (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val email: String,
    val password : String
) : Parcelable