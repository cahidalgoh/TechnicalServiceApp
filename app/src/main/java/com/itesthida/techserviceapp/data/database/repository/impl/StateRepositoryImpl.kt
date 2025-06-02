package com.itesthida.techserviceapp.data.database.repository.impl

import android.content.ContentValues
import android.content.Context
import com.itesthida.techserviceapp.data.database.DatabaseHelper
import com.itesthida.techserviceapp.data.database.entities.State
import com.itesthida.techserviceapp.data.database.repository.StateRepository

class StateRepositoryImpl(private val context: Context) : StateRepository {
    // Instancia para la conexión con la base de datos
    private val dbHelper = DatabaseHelper.getInstance(context)

    override fun insert(state: State) {
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val values = ContentValues().apply {
                put(State.COLUMN_NAME_STATE_NAME, state.stateName)
            }
            it.insert(State.TABLE_NAME, null, values)
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
    }

    override fun getAll(): List<State> {
        val states = mutableListOf<State>()
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val cursor = it.query(
                State.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
            )
            cursor?.use { c ->
                while(c.moveToNext()){
                    val state = State(
                        id = c.getLong(c.getColumnIndexOrThrow(State.COLUMN_NAME_ID)),
                        stateName = c.getString(c.getColumnIndexOrThrow(State.COLUMN_NAME_STATE_NAME))
                    )
                    states.add(state)
                }
            }
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
        return states
    }

    override fun getById(id: Int): State? {
        var state : State? = null
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val cursor = it.query(
                State.TABLE_NAME,
                null,
                "${State.COLUMN_NAME_ID} = ?",
                arrayOf(id.toString()),
                null,
                null,
                null
            )

            cursor?.use { c ->
                if(cursor.moveToFirst()){
                    state = State(
                        id = c.getLong(c.getColumnIndexOrThrow(State.COLUMN_NAME_ID)),
                        stateName = c.getString(c.getColumnIndexOrThrow(State.COLUMN_NAME_STATE_NAME))
                    )
                }
            }
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
        return state
    }

    override fun update(state: State) {
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val values = ContentValues().apply {
                put(State.COLUMN_NAME_STATE_NAME, state.stateName)
            }
            it.update(
                State.TABLE_NAME,
                values,
                "${state.id} = ?",
                arrayOf(state.id.toString())
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
                State.TABLE_NAME,
                "${State.COLUMN_NAME_ID} = ?",
                arrayOf(id.toString())
            )
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
    }
}