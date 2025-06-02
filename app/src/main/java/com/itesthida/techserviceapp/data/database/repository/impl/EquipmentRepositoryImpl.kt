package com.itesthida.techserviceapp.data.database.repository.impl

import android.content.ContentValues
import android.content.Context
import com.itesthida.techserviceapp.data.database.DatabaseHelper
import com.itesthida.techserviceapp.data.database.entities.Customer
import com.itesthida.techserviceapp.data.database.entities.Equipment
import com.itesthida.techserviceapp.data.database.entities.EquipmentType
import com.itesthida.techserviceapp.data.database.repository.EquipmentRepository

class EquipmentRepositoryImpl(private val context: Context) : EquipmentRepository {
    // Instancia para la conexión con la base de datos
    private val dbHelper = DatabaseHelper.getInstance(context)
    private  val customerRepository = CustomerRepositoryImpl(context)
    private  val equipmentTypeRepository = EquipmentTypeRepositoryImpl(context)

    override fun insert(equipment: Equipment) {
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val values = ContentValues().apply {
                put(Equipment.COLUMN_NAME_CUSTOMER, equipment.customer.id)
                put(Equipment.COLUMN_NAME_EQUIPMENT_TYPE, equipment.equipmentType.id)
                put(Equipment.COLUMN_NAME_SERIAL_NUMBER, equipment.serialNumber)
            }
            it.insert(
                Equipment.TABLE_NAME,
                null,
                values
            )
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
    }

    override fun getAll(): List<Equipment> {
        val equipments = mutableListOf<Equipment>()
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val cursor = it.query(
                Equipment.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
            )

            cursor?.use { c ->
                while (c.moveToNext()){
                    val equipment = Equipment(
                        id = c.getInt(c.getColumnIndexOrThrow(Equipment.COLUMN_NAME_ID)),
                        customer = customerRepository.getById(
                            c.getInt(c.getColumnIndexOrThrow(Equipment.COLUMN_NAME_CUSTOMER))
                        ) ?: Customer(
                            Customer.DEFAULT_ID, "", "", "", "", ""
                        ),
                        equipmentType = equipmentTypeRepository.getById(
                            c.getInt(c.getColumnIndexOrThrow(Equipment.COLUMN_NAME_EQUIPMENT_TYPE))
                        ) ?: EquipmentType(
                            EquipmentType.DEFAULT_ID, "", "", ""
                        ),
                        serialNumber = c.getString(c.getColumnIndexOrThrow(Equipment.COLUMN_NAME_SERIAL_NUMBER))
                    )
                    // Añadimos el Equipo a la lista
                    equipments.add(equipment)
                }
            }
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
        return  equipments
    }

    override fun getById(id: Int): Equipment? {
        var equipment : Equipment ? = null
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val cursor = it.query(
                Equipment.TABLE_NAME,
                null,
                "${Equipment.COLUMN_NAME_ID} = ?",
                arrayOf(id.toString()),
                null,
                null,
                null
            )

            cursor?.use { c ->
                if(c.moveToFirst()){
                    equipment = Equipment(
                        id = c.getInt(c.getColumnIndexOrThrow(Equipment.COLUMN_NAME_ID)),
                        customer = customerRepository.getById(
                            c.getInt(c.getColumnIndexOrThrow(Equipment.COLUMN_NAME_CUSTOMER))
                        ) ?: Customer(
                            Customer.DEFAULT_ID, "", "", "", "", ""
                        ),
                        equipmentType = equipmentTypeRepository.getById(
                            c.getInt(c.getColumnIndexOrThrow(Equipment.COLUMN_NAME_EQUIPMENT_TYPE))
                        ) ?: EquipmentType(
                            EquipmentType.DEFAULT_ID, "", "", ""
                        ),
                        serialNumber = c.getString(c.getColumnIndexOrThrow(Equipment.COLUMN_NAME_SERIAL_NUMBER))
                    )
                }
            }
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
        return equipment
    }

    override fun update(equipment: Equipment) {
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val values = ContentValues().apply {
                put(Equipment.COLUMN_NAME_CUSTOMER, equipment.customer.id)
                put(Equipment.COLUMN_NAME_EQUIPMENT_TYPE, equipment.equipmentType.id)
                put(Equipment.COLUMN_NAME_SERIAL_NUMBER, equipment.serialNumber)
            }

            it.update(
                Equipment.TABLE_NAME,
                values,
                "${Equipment.COLUMN_NAME_ID} = ?",
                arrayOf(equipment.id.toString())
            )
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
    }

    override fun delete(id: Int) {
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            it.delete(
                Equipment.TABLE_NAME,
                "${Equipment.COLUMN_NAME_ID} = ?",
                arrayOf(id.toString())
            )
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
    }
}