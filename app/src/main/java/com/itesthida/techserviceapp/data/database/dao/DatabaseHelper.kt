package com.itesthida.techserviceapp.data.database.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.itesthida.techserviceapp.data.database.entities.Customer
import com.itesthida.techserviceapp.data.database.entities.Equipment
import com.itesthida.techserviceapp.data.database.entities.EquipmentType
import com.itesthida.techserviceapp.data.database.entities.State
import com.itesthida.techserviceapp.data.database.entities.TaskOrder
import com.itesthida.techserviceapp.data.database.entities.Technician

class DatabaseHelper( context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // Si cambia el esquema de la base de datos, debe incrementar la versión de la base de datos
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "technical_service.db"

        // Constante para la creación de la tabla TECHNICIANS
        private const val SQL_CREATE_TECHNICIANS =
            "CREATE TABLE ${Technician.TABLE_NAME} (" +
                    "${Technician.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "${Technician.COLUMN_NAME_NAME} TEXT, " +
                    "${Technician.COLUMN_NAME_LAST_NAME} TEXT, " +
                    "${Technician.COLUMN_NAME_EMAIL} TEXT, " +
                    "${Technician.COLUMN_NAME_PASSWORD} TEXT)"
        // Constante para la eliminación de la tabla TECHNICIANS
        private const val SQL_DELETE_TECHNICIANS = "DROP TABLE IF EXISTS ${Technician.TABLE_NAME}"


        // Constante para la creación de la tabla CUSTOMERS
        private const val SQL_CREATE_CUSTOMERS =
            "CREATE TABLE ${Customer.TABLE_NAME} (" +
                    "${Customer.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "${Customer.COLUMN_NAME_NAME} TEXT, " +
                    "${Customer.COLUMN_NAME_LAST_NAME} TEXT, " +
                    "${Customer.COLUMN_NAME_EMAIL} TEXT, " +
                    "${Customer.COLUMN_NAME_PHONE} TEXT, " +
                    "${Customer.COLUMN_NAME_ADDRESS} TEXT)"
        // Constante para la eliminación de la tabla CUSTOMERS
        private const val SQL_DELETE_CUSTOMERS = "DROP TABLE IF EXISTS ${Customer.TABLE_NAME}"


        // Constante para la creación de la tabla EQUIPMENT_TYPES
        private const val SQL_CREATE_EQUIPMENT_TYPES =
            "CREATE TABLE ${EquipmentType.TABLE_NAME} (" +
                    "${EquipmentType.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "${EquipmentType.COLUMN_NAME_NAME} TEXT, " +
                    "${EquipmentType.COLUMN_NAME_BRAND} TEXT, " +
                    "${EquipmentType.COLUMN_NAME_MODEL} TEXT)"
        // Constante para la eliminación de la tabla EQUIPMENT_TYPES
        private const val SQL_DELETE_EQUIPMENT_TYPES = "DROP TABLE IF EXISTS ${EquipmentType.TABLE_NAME}"


        // Constante para la creación de la tabla STATES
        private const val SQL_CREATE_STATES =
            "CREATE TABLE ${State.TABLE_NAME} (" +
                    "${State.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "${State.COLUMN_NAME_STATE_NAME} TEXT)"
        // Constante para la eliminación de la tabla CUSTOMERS
        private const val SQL_DELETE_STATES = "DROP TABLE IF EXISTS ${State.TABLE_NAME}"


        // Constante para la creación de la tabla EQUIPMENTS
        private const val SQL_CREATE_EQUIPMENT =
            "CREATE TABLE ${Equipment.TABLE_NAME} (" +
                    "${Equipment.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "${Equipment.COLUMN_NAME_CUSTOMER} INTEGER, " +
                    "${Equipment.COLUMN_NAME_EQUIPMENT_TYPE} INTEGER, " +
                    "${Equipment.COLUMN_NAME_SERIAL_NUMBER} TEXT)"
        // Constante para la eliminación de la tabla EQUIPMENTS
        private const val SQL_DELETE_EQUIPMENT = "DROP TABLE IF EXISTS ${Equipment.TABLE_NAME}"


        // Constante para la creación de la tabla TASK_ORDERS
        private const val SQL_CREATE_TASK_ORDERS =
            "CREATE TABLE ${TaskOrder.TABLE_NAME} (" +
                    "${TaskOrder.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "${TaskOrder.COLUMN_NAME_TECHNICIAN} INTEGER, " +
                    "${TaskOrder.COLUMN_NAME_CUSTOMER} INTEGER, " +
                    "${TaskOrder.COLUMN_NAME_EQUIPMENT} INTEGER, " +
                    "${TaskOrder.COLUMN_NAME_DESCRIPTION} TEXT, " +
                    "${TaskOrder.COLUMN_NAME_DATE} INTEGER, " +
                    "${TaskOrder.COLUMN_NAME_STATE} INTEGER)"
        // Constante para la eliminación de la tabla TASK_ORDERS
        private const val SQL_DELETE_TASK_ORDERS = "DROP TABLE IF EXISTS ${TaskOrder.TABLE_NAME}"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TECHNICIANS)
        db.execSQL(SQL_CREATE_CUSTOMERS)
        db.execSQL(SQL_CREATE_EQUIPMENT_TYPES)
        db.execSQL(SQL_CREATE_STATES)
        db.execSQL(SQL_CREATE_EQUIPMENT)
        db.execSQL(SQL_CREATE_TASK_ORDERS)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onDestroy(db)
        onCreate(db)
    }

    /**
     * Función que se llama cuando existe una modificación en la base de datos
     *
     * @param db The database.
     */
    fun onDestroy(db: SQLiteDatabase) {
        db.execSQL(SQL_DELETE_TASK_ORDERS)
        db.execSQL(SQL_DELETE_EQUIPMENT)
        db.execSQL(SQL_DELETE_STATES)
        db.execSQL(SQL_DELETE_EQUIPMENT_TYPES)
        db.execSQL(SQL_DELETE_CUSTOMERS)
        db.execSQL(SQL_DELETE_TECHNICIANS)
    }
}