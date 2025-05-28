package com.itesthida.techserviceapp.data.database.entities

//import androidx.room.Entity
//import androidx.room.PrimaryKey

//@Entity(tableName = "Technicians")
data class Technician (
    //@PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val lastName: String,
    val email: String,
    val password: String
){
    companion object{
        // Constante para el id por defecto
        const val DEFAULT_ID = -1L
        // Constantes para los datos de la tabla, nombre de tabla y columnas
        const val TABLE_NAME = "TECHNICIANS"
        const val COLUMN_NAME_ID = "ID"
        const val COLUMN_NAME_NAME = "NAME"
        const val COLUMN_NAME_LAST_NAME = "LAST_NAME"
        const val COLUMN_NAME_EMAIL = "EMAIL"
        const val COLUMN_NAME_PASSWORD = "PASSWORD"
    }
}
