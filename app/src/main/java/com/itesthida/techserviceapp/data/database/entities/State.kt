package com.itesthida.techserviceapp.data.database.entities

data class State(
    val id: Int = 0,
    val stateName: String
) {
    companion object{
        // Constantes para los datos de la tabla, nombre de tabla y columnas
        const val TABLE_NAME = "STATES"
        const val COLUMN_NAME_ID = "ID"
        const val COLUMN_NAME_NAME = "STATE_NAME"
    }
}