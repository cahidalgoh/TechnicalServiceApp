package com.itesthida.techserviceapp.data.database.repository.impl

import android.content.ContentValues
import android.content.Context
import com.itesthida.techserviceapp.data.database.DatabaseHelper
import com.itesthida.techserviceapp.data.database.entities.Customer
import com.itesthida.techserviceapp.data.database.entities.Equipment
import com.itesthida.techserviceapp.data.database.entities.EquipmentType
import com.itesthida.techserviceapp.data.database.repository.CustomerRepository

class CustomerRepositoryImpl(private val context: Context) : CustomerRepository {
    // Instancia para la conexión con la base de datos
    private val dbHelper = DatabaseHelper.getInstance(context)

    override fun insert(customer: Customer): Long? {
        var newRowId : Long? = -1L
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val values = ContentValues().apply {
                put(Customer.COLUMN_NAME_NAME, customer.name)
                put(Customer.COLUMN_NAME_LAST_NAME, customer.lastName)
                put(Customer.COLUMN_NAME_EMAIL, customer.email)
                put(Customer.COLUMN_NAME_PHONE, customer.phone)
                put(Customer.COLUMN_NAME_ADDRESS, customer.address)
            }
            newRowId = it.insert(Customer.TABLE_NAME, null, values)
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
        return newRowId
    }

    override fun getAll(): List<Customer> {
        val customers = mutableListOf<Customer>()
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val cursor = it.query(
                Customer.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
            )
            cursor?.use { c ->
                while(c.moveToNext()){
                    val customer = Customer(
                        id = c.getLong(c.getColumnIndexOrThrow(Customer.COLUMN_NAME_ID)),
                        name = c.getString(c.getColumnIndexOrThrow(Customer.COLUMN_NAME_NAME)),
                        lastName = c.getString(c.getColumnIndexOrThrow(Customer.COLUMN_NAME_LAST_NAME)),
                        email = c.getString(c.getColumnIndexOrThrow(Customer.COLUMN_NAME_EMAIL)),
                        phone = c.getString(c.getColumnIndexOrThrow(Customer.COLUMN_NAME_PHONE)),
                        address = c.getString(c.getColumnIndexOrThrow(Customer.COLUMN_NAME_ADDRESS))
                    )
                    // Obtenemos los equipos asociados
                    customer.equipments = getEquipmentsByCustomerId(customer.id)

                    customers.add(customer)
                }
            }
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
        return customers
    }

    override fun getById(id: Long): Customer? {
        var customer : Customer? = null
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val cursor = it.query(
                Customer.TABLE_NAME,
                null,
                "${Customer.COLUMN_NAME_ID} = ?",
                arrayOf(id.toString()),
                null,
                null,
                null
            )

            cursor?.use { c ->
                if(c.moveToFirst()){
                    customer = Customer(
                        id = c.getLong(c.getColumnIndexOrThrow(Customer.COLUMN_NAME_ID)),
                        name = c.getString(c.getColumnIndexOrThrow(Customer.COLUMN_NAME_NAME)),
                        lastName = c.getString(c.getColumnIndexOrThrow(Customer.COLUMN_NAME_LAST_NAME)),
                        email = c.getString(c.getColumnIndexOrThrow(Customer.COLUMN_NAME_EMAIL)),
                        phone = c.getString(c.getColumnIndexOrThrow(Customer.COLUMN_NAME_PHONE)),
                        address = c.getString(c.getColumnIndexOrThrow(Customer.COLUMN_NAME_ADDRESS))
                    )
                }
            }
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
        return customer
    }

    override fun update(customer: Customer) {
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val values = ContentValues().apply {
                put(Customer.COLUMN_NAME_NAME, customer.name)
                put(Customer.COLUMN_NAME_LAST_NAME, customer.lastName)
                put(Customer.COLUMN_NAME_EMAIL, customer.email)
                put(Customer.COLUMN_NAME_PHONE, customer.phone)
                put(Customer.COLUMN_NAME_ADDRESS, customer.address)
            }
            it.update(
                Customer.TABLE_NAME,
                values,
                "${Customer.COLUMN_NAME_ID} = ?",
                arrayOf(customer.id.toString())
            )
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
    }

    override fun delete(id: Long) {
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            it.delete(
                Customer.TABLE_NAME,
                "${Customer.COLUMN_NAME_ID} = ?",
                arrayOf(id.toString())
            )
        }
        // Cerramos la conexión con la base de datos
        dbHelper.closeConnection()
    }

    private fun getEquipmentsByCustomerId(customerId: Long) : List<Equipment>{
        val equipments = mutableListOf<Equipment>()
        // Abrimos conexión con la base de datos
        dbHelper.openConnection()
        val db = dbHelper.getDatabase()

        db?.let {
            val cursor = it.query(
                Equipment.TABLE_NAME,
                null,
                "${Equipment.COLUMN_NAME_CUSTOMER} = ?",
                arrayOf(customerId.toString()),
                null,
                null,
                null
            )

            cursor?.use { c ->
                while (c.moveToNext()){
                    val equipment = Equipment(
                        id = c.getLong(c.getColumnIndexOrThrow(Equipment.COLUMN_NAME_ID)),
                        customer = Customer(
                            customerId, "", "", "", "", ""
                        ),
                        equipmentType = EquipmentTypeRepositoryImpl(context).getById(
                            c.getLong(c.getColumnIndexOrThrow(Equipment.COLUMN_NAME_EQUIPMENT_TYPE))
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
}
