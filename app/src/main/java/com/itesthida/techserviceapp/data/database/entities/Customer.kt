package com.itesthida.techserviceapp.data.database.entities

data class Customer(
    val id: Long,
    val name: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val address: String
) {
    lateinit var equipments: List<Equipment>
    companion object{
        // Constante para el id por defecto
        const val DEFAULT_ID = -1L
        // Constantes para los datos de la tabla, nombre de tabla y columnas
        const val TABLE_NAME = "CUSTOMERS"
        const val COLUMN_NAME_ID = "ID"
        const val COLUMN_NAME_NAME = "NAME"
        const val COLUMN_NAME_LAST_NAME = "LAST_NAME"
        const val COLUMN_NAME_EMAIL = "EMAIL"
        const val COLUMN_NAME_PHONE = "PHONE"
        const val COLUMN_NAME_ADDRESS = "ADDRESS"
    }
}
