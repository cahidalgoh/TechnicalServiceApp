package com.itesthida.techserviceapp.data.database.repository.impl

import android.content.ContentValues
import android.content.Context
import com.itesthida.techserviceapp.data.database.DatabaseHelper
import com.itesthida.techserviceapp.data.database.entities.EquipmentType
import com.itesthida.techserviceapp.data.database.repository.EquipmentTypeRepository

class EquipmentTypeRepositoryImpl(private val context: Context) : EquipmentTypeRepository {
    // Instancia para la conexión con la base de datos
    private val dbHelper = DatabaseHelper.getInstance(context)

    override fun insert(equipmentType: EquipmentType) {
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let{
            val values = ContentValues().apply {
                put(EquipmentType.COLUMN_NAME_NAME, equipmentType.name)
                put(EquipmentType.COLUMN_NAME_BRAND, equipmentType.brand)
                put(EquipmentType.COLUMN_NAME_MODEL, equipmentType.model)
            }
            it.insert(EquipmentType.TABLE_NAME, null, values)
        }
        dbHelper.closeConnection()
    }

    override fun getAll(): List<EquipmentType> {
        val equipmentTypes = mutableListOf<EquipmentType>()
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val cursor = it.query(
                EquipmentType.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
            )

            cursor?.use { c ->
                while (c.moveToNext()){
                    val equipmentType = EquipmentType(
                        id = c.getInt(c.getColumnIndexOrThrow(EquipmentType.COLUMN_NAME_ID)),
                        name = c.getString(c.getColumnIndexOrThrow(EquipmentType.COLUMN_NAME_NAME)),
                        brand = c.getString(c.getColumnIndexOrThrow(EquipmentType.COLUMN_NAME_BRAND)),
                        model = c.getString(c.getColumnIndexOrThrow(EquipmentType.COLUMN_NAME_MODEL))
                    )
                    equipmentTypes.add(equipmentType)
                }
            }
        }
        // Cerramos conexión con la base de datos
        dbHelper.closeConnection()
        return equipmentTypes
    }

    override fun getById(id: Int): EquipmentType? {
        var equipmentType : EquipmentType? = null
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val cursor = it.query(
                EquipmentType.TABLE_NAME,
                null,
                "${EquipmentType.COLUMN_NAME_ID} = ?",
                arrayOf(id.toString()),
                null,
                null,
                null
            )

            cursor?.use { c ->
                if(c.moveToFirst()){
                    equipmentType = EquipmentType(
                        id = c.getInt(c.getColumnIndexOrThrow(EquipmentType.COLUMN_NAME_ID)),
                        name = c.getString(c.getColumnIndexOrThrow(EquipmentType.COLUMN_NAME_NAME)),
                        brand = c.getString(c.getColumnIndexOrThrow(EquipmentType.COLUMN_NAME_BRAND)),
                        model = c.getString(c.getColumnIndexOrThrow(EquipmentType.COLUMN_NAME_MODEL))
                    )
                }
            }
        }
        // Cerramos la conexión
        dbHelper.closeConnection()
        return equipmentType
    }

    override fun update(equipmentType: EquipmentType) {
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val values = ContentValues().apply {
                put(EquipmentType.COLUMN_NAME_NAME, equipmentType.name)
                put(EquipmentType.COLUMN_NAME_BRAND, equipmentType.brand)
                put(EquipmentType.COLUMN_NAME_MODEL, equipmentType.model)
            }
            it.update(
                EquipmentType.TABLE_NAME,
                values,
                "${equipmentType.id} = ?",
                arrayOf(equipmentType.id.toString())
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
                EquipmentType.TABLE_NAME,
                "${EquipmentType.COLUMN_NAME_ID} = ?",
                arrayOf(id.toString())
            )
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
    }
}