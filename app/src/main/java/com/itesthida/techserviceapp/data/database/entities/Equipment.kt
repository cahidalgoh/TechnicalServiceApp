package com.itesthida.techserviceapp.data.database.entities

data class Equipment(
    val id: Int,
    val customer: Customer,
    val equipmentType: EquipmentType,
    val serialNumber: String
) {
    companion object{
        // Constante para el id por defecto
        const val DEFAULT_ID = -1
        // Constantes para los datos de la tabla, nombre de tabla y columnas
        const val TABLE_NAME = "EQUIPMENTS"
        const val COLUMN_NAME_ID = "ID"
        const val COLUMN_NAME_CUSTOMER = "CUSTOMER_ID"
        const val COLUMN_NAME_EQUIPMENT_TYPE = "EQUIPMENT_TYPE_ID"
        const val COLUMN_NAME_SERIAL_NUMBER = "SERIAL_NUMBER"
    }
}