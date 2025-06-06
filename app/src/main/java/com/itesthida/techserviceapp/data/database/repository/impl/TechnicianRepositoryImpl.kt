package com.itesthida.techserviceapp.data.database.repository.impl

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.itesthida.techserviceapp.data.database.DatabaseHelper
import com.itesthida.techserviceapp.data.database.entities.Technician
import com.itesthida.techserviceapp.data.database.repository.TechnicianRepository
import com.itesthida.techserviceapp.utils.TechServiceUtils

class TechnicianRepositoryImpl(private val context: Context) : TechnicianRepository {
    // Instancia para la conexión con la base de datos
    private val dbHelper = DatabaseHelper.getInstance(context)

    override fun insert(technician: Technician) {
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let{
            val values = ContentValues().apply {
                put(Technician.COLUMN_NAME_NAME, technician.name)
                put(Technician.COLUMN_NAME_LAST_NAME, technician.lastName)
                put(Technician.COLUMN_NAME_EMAIL, technician.email)
                put(Technician.COLUMN_NAME_PASSWORD, technician.password)
            }
            val rowId = it.insert(Technician.TABLE_NAME, null, values)
            Log.d("DB", "Técnico insertado con ID: $rowId")
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
    }

    override fun getAll(): List<Technician> {
        val technicians = mutableListOf<Technician>()
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val cursor = it.query(
                Technician.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
            )
            cursor?.use { c ->
                while(c.moveToNext()){
                    val technician = Technician(
                        id = c.getLong(c.getColumnIndexOrThrow(Technician.COLUMN_NAME_ID)),
                        name = c.getString(c.getColumnIndexOrThrow(Technician.COLUMN_NAME_NAME)),
                        lastName = c.getString(c.getColumnIndexOrThrow(Technician.COLUMN_NAME_LAST_NAME)),
                        email = c.getString(c.getColumnIndexOrThrow(Technician.COLUMN_NAME_EMAIL)),
                        password = c.getString(c.getColumnIndexOrThrow(Technician.COLUMN_NAME_PASSWORD))
                    )
                    // Añadimos el técnico recuperado a la lista
                    technicians.add(technician)
                }
            }
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
        return technicians
    }

    override fun getById(id: Int): Technician? {
        var technician : Technician? = null
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val cursor = it.query(
                Technician.TABLE_NAME,
                null,
                "${Technician.COLUMN_NAME_ID} = ?",
                arrayOf(id.toString()),
                null,
                null,
                null
            )
            cursor?.use { c ->
                if(c.moveToFirst()){
                    technician = Technician(
                        id = c.getLong(c.getColumnIndexOrThrow(Technician.COLUMN_NAME_ID)),
                        name = c.getString(c.getColumnIndexOrThrow(Technician.COLUMN_NAME_NAME)),
                        lastName = c.getString(c.getColumnIndexOrThrow(Technician.COLUMN_NAME_LAST_NAME)),
                        email = c.getString(c.getColumnIndexOrThrow(Technician.COLUMN_NAME_EMAIL)),
                        password = c.getString(c.getColumnIndexOrThrow(Technician.COLUMN_NAME_PASSWORD))
                    )
                }
            }
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
        return technician
    }

    override fun update(technician: Technician) {
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val values = ContentValues().apply {
                put(Technician.COLUMN_NAME_NAME, technician.name)
                put(Technician.COLUMN_NAME_LAST_NAME, technician.lastName)
                put(Technician.COLUMN_NAME_EMAIL, technician.email)
                put(Technician.COLUMN_NAME_PASSWORD, TechServiceUtils.hashPassword(technician.password))
            }
            it.update(
                Technician.TABLE_NAME,
                values,
                "${Technician.COLUMN_NAME_ID} = ?",
                arrayOf(technician.id.toString())
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
                Technician.TABLE_NAME,
                "${Technician.COLUMN_NAME_ID} = ?",
                arrayOf(id.toString())
            )
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
    }

    override fun login(email: String, password: String): Technician? {

        var technician : Technician? = null
        // Hash del password para comparar
        val hashedPassword = TechServiceUtils.hashPassword(password)

        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val cursor = it.query(
                Technician.TABLE_NAME,
                null,
                "${Technician.COLUMN_NAME_EMAIL} = ? AND ${Technician.COLUMN_NAME_PASSWORD} = ?",
                arrayOf(email, hashedPassword),
                null,
                null,
                null
            )

            cursor?.use { c ->
                if(c.moveToFirst()){
                    technician = Technician(
                        id = c.getLong(c.getColumnIndexOrThrow(Technician.COLUMN_NAME_ID)),
                        name = c.getString(c.getColumnIndexOrThrow(Technician.COLUMN_NAME_NAME)),
                        lastName = c.getString(c.getColumnIndexOrThrow(Technician.COLUMN_NAME_LAST_NAME)),
                        email = c.getString(c.getColumnIndexOrThrow(Technician.COLUMN_NAME_EMAIL)),
                        password = c.getString(c.getColumnIndexOrThrow(Technician.COLUMN_NAME_PASSWORD))
                    )
                }
            }
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
        return technician
    }

    override fun getByEmail(email: String): Technician? {

        var technician : Technician? = null

        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val cursor = it.query(
                Technician.TABLE_NAME,
                null,
                "${Technician.COLUMN_NAME_EMAIL} = ?",
                arrayOf(email),
                null,
                null,
                null
            )

            cursor?.use { c ->
                if(c.moveToFirst()){
                    technician = Technician(
                        id = c.getLong(c.getColumnIndexOrThrow(Technician.COLUMN_NAME_ID)),
                        name = c.getString(c.getColumnIndexOrThrow(Technician.COLUMN_NAME_NAME)),
                        lastName = c.getString(c.getColumnIndexOrThrow(Technician.COLUMN_NAME_LAST_NAME)),
                        email = c.getString(c.getColumnIndexOrThrow(Technician.COLUMN_NAME_EMAIL)),
                        password = c.getString(c.getColumnIndexOrThrow(Technician.COLUMN_NAME_PASSWORD))
                    )
                }
            }
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
        return technician
    }
}