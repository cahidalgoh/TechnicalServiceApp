package com.itesthida.techserviceapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Technicians")
data class Technician (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val lastName: String,
    val email: String,
    val password: String
)