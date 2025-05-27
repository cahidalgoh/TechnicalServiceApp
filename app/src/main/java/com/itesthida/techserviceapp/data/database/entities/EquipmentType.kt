package com.itesthida.techserviceapp.data.database.entities

data class EquipmentType(
    val id: Int,
    val name: String,
    val brand: String,
    val model: String
) {
    companion object{
        // Constante para el id por defecto
        const val DEFAULT_ID = -1L
        // Constantes para los datos de la tabla, nombre de tabla y columnas
        const val TABLE_NAME = "EQUIPMENT_TYPES"
        const val COLUMN_NAME_ID = "ID"
        const val COLUMN_NAME_NAME = "NAME"
        const val COLUMN_NAME_BRAND = "BRAND"
        const val COLUMN_NAME_MODEL = "MODEL"
    }
}