package com.itesthida.techserviceapp.data.database.entities

data class TaskOrder(
    val id: Long,
    val technician: Technician,
    val customer: Customer,
    val equipment: Equipment,
    val description: String,
    val date: Int,
    val state: State
) {
    companion object{
        // Constante para el id por defecto
        const val DEFAULT_ID = -1L
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