package com.itesthida.techserviceapp.data.database.repository.impl

import android.content.ContentValues
import android.content.Context
import com.itesthida.techserviceapp.data.database.DatabaseHelper
import com.itesthida.techserviceapp.data.database.entities.Customer
import com.itesthida.techserviceapp.data.database.entities.Equipment
import com.itesthida.techserviceapp.data.database.entities.EquipmentType
import com.itesthida.techserviceapp.data.database.entities.State
import com.itesthida.techserviceapp.data.database.entities.TaskOrder
import com.itesthida.techserviceapp.data.database.entities.Technician
import com.itesthida.techserviceapp.data.database.repository.TaskOrderRepository

class TaskOrderRepositoryImpl(private val context: Context) : TaskOrderRepository {
    // Instancia para la conexión con la base de datos
    private val dbHelper = DatabaseHelper.getInstance(context)

    override fun insert(taskOrder: TaskOrder) {
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val values = ContentValues().apply {
                put(TaskOrder.COLUMN_NAME_TECHNICIAN, taskOrder.technician.id)
                put(TaskOrder.COLUMN_NAME_CUSTOMER, taskOrder.customer.id)
                put(TaskOrder.COLUMN_NAME_EQUIPMENT, taskOrder.equipment.id)
                put(TaskOrder.COLUMN_NAME_DESCRIPTION, taskOrder.description)
                put(TaskOrder.COLUMN_NAME_DATE, taskOrder.date)
                put(TaskOrder.COLUMN_NAME_STATE, taskOrder.state.id)
            }
            it.insert(TaskOrder.TABLE_NAME, null, values)
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
    }

    override fun getAll(): List<TaskOrder> {
        val taskOrders = mutableListOf<TaskOrder>()
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val cursor = it.query(
                TaskOrder.TABLE_NAME,
                null,
                null, null, null, null, null
            )
            cursor?.use { c ->
                while (c.moveToNext()) {
                    val taskOrder = TaskOrder(
                        id = c.getInt(c.getColumnIndexOrThrow(TaskOrder.COLUMN_NAME_ID)),
                        technician = TechnicianRepositoryImpl(context).getById(c.getInt(c.getColumnIndexOrThrow(TaskOrder.COLUMN_NAME_TECHNICIAN))) ?: Technician(Technician.DEFAULT_ID, "", "", "", ""),
                        customer = CustomerRepositoryImpl(context).getById(c.getInt(c.getColumnIndexOrThrow(TaskOrder.COLUMN_NAME_CUSTOMER))) ?: Customer(Customer.DEFAULT_ID, "", "", "", "", ""),
                        equipment = EquipmentRepositoryImpl(context).getById(c.getInt(c.getColumnIndexOrThrow(TaskOrder.COLUMN_NAME_EQUIPMENT))) ?: Equipment(Equipment.DEFAULT_ID, Customer(Customer.DEFAULT_ID, "", "", "", "", ""), EquipmentType(
                            EquipmentType.DEFAULT_ID, "", "", ""), ""),
                        description = c.getString(c.getColumnIndexOrThrow(TaskOrder.COLUMN_NAME_DESCRIPTION)),
                        date = c.getInt(c.getColumnIndexOrThrow(TaskOrder.COLUMN_NAME_DATE)),
                        state = StateRepositoryImpl(context).getById(c.getInt(c.getColumnIndexOrThrow(TaskOrder.COLUMN_NAME_STATE))) ?: State(State.DEFAULT_ID, "")
                    )
                    taskOrders.add(taskOrder)
                }
            }
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
        return taskOrders
    }

    override fun getById(id: Int): TaskOrder? {
        var taskOrder : TaskOrder? = null
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val cursor = it.query(
                TaskOrder.TABLE_NAME,
                null,
                "${TaskOrder.COLUMN_NAME_ID} = ?",
                arrayOf(id.toString()),
                null, null, null
            )
            cursor?.use { c ->
                if (c.moveToFirst()) {
                    taskOrder = TaskOrder(
                        id = c.getInt(c.getColumnIndexOrThrow(TaskOrder.COLUMN_NAME_ID)),
                        technician = TechnicianRepositoryImpl(context).getById(c.getInt(c.getColumnIndexOrThrow(TaskOrder.COLUMN_NAME_TECHNICIAN))) ?: Technician(Technician.DEFAULT_ID, "", "", "", ""),
                        customer = CustomerRepositoryImpl(context).getById(c.getInt(c.getColumnIndexOrThrow(TaskOrder.COLUMN_NAME_CUSTOMER))) ?: Customer(Customer.DEFAULT_ID, "", "", "", "", ""),
                        equipment = EquipmentRepositoryImpl(context).getById(c.getInt(c.getColumnIndexOrThrow(TaskOrder.COLUMN_NAME_EQUIPMENT))) ?: Equipment(Equipment.DEFAULT_ID, Customer(Customer.DEFAULT_ID, "", "", "", "", ""), EquipmentType(EquipmentType.DEFAULT_ID, "", "", ""), ""),
                        description = c.getString(c.getColumnIndexOrThrow(TaskOrder.COLUMN_NAME_DESCRIPTION)),
                        date = c.getInt(c.getColumnIndexOrThrow(TaskOrder.COLUMN_NAME_DATE)),
                        state = StateRepositoryImpl(context).getById(c.getInt(c.getColumnIndexOrThrow(TaskOrder.COLUMN_NAME_STATE))) ?: State(State.DEFAULT_ID, "")
                    )
                }
            }
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
        return taskOrder
    }

    override fun update(taskOrder: TaskOrder) {
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val values = ContentValues().apply {
                put(TaskOrder.COLUMN_NAME_TECHNICIAN, taskOrder.technician.id)
                put(TaskOrder.COLUMN_NAME_CUSTOMER, taskOrder.customer.id)
                put(TaskOrder.COLUMN_NAME_EQUIPMENT, taskOrder.equipment.id)
                put(TaskOrder.COLUMN_NAME_DESCRIPTION, taskOrder.description)
                put(TaskOrder.COLUMN_NAME_DATE, taskOrder.date)
                put(TaskOrder.COLUMN_NAME_STATE, taskOrder.state.id)
            }
            it.update(
                TaskOrder.TABLE_NAME,
                values,
                "${TaskOrder.COLUMN_NAME_ID} = ?",
                arrayOf(taskOrder.id.toString())
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
                TaskOrder.TABLE_NAME,
                "${TaskOrder.COLUMN_NAME_ID} = ?",
                arrayOf(id.toString())
            )
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
    }
}