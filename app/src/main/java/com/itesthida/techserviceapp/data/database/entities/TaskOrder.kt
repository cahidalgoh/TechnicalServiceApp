package com.itesthida.techserviceapp.data.database.entities

data class TaskOrder(
    val id: Int = 0,
    val technician: Int,
    val customer: Int,
    val equipment: Int,
    val description: String,
    val date: Int,
    val state: Int
) {
    companion object{
        // Constantes para los datos de la tabla, nombre de tabla y columnas
        const val TABLE_NAME = "TASK_ORDERS"
        const val COLUMN_NAME_ID = "ID"
        const val COLUMN_NAME_TECHNICIAN = "TECHNICIAN_ID"
        const val COLUMN_NAME_CUSTOMER = "CUSTOMER_ID"
        const val COLUMN_NAME_EQUIPMENT = "EQUIPMENT_ID"
        const val COLUMN_NAME_DESCRIPTION = "DESCRIPTION"
        const val COLUMN_NAME_DATE = "DATE"
        const val COLUMN_NAME_STATE = "STATE_ID"
    }
}