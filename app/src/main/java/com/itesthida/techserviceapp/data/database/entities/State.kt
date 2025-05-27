package com.itesthida.techserviceapp.data.database.entities

data class State(
    val id: Int,
    val stateName: String
) {
    companion object{
        // Constante para el di por defecto
        const val DEFAULT_ID = -1L
        // Constantes para los datos de la tabla, nombre de tabla y columnas
        const val TABLE_NAME = "STATES"
        const val COLUMN_NAME_ID = "ID"
        const val COLUMN_NAME_STATE_NAME = "STATE_NAME"
    }
}