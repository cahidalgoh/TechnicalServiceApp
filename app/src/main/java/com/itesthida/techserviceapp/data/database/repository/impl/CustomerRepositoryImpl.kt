package com.itesthida.techserviceapp.data.database.repository.impl

import android.content.ContentValues
import android.content.Context
import com.itesthida.techserviceapp.data.database.DatabaseHelper
import com.itesthida.techserviceapp.data.database.entities.Customer
import com.itesthida.techserviceapp.data.database.repository.CustomerRepository

class CustomerRepositoryImpl(private val context: Context) : CustomerRepository {
    // Instancia para la conexión con la base de datos
    private val dbHelper = DatabaseHelper.getInstance(context)

    override fun insert(customer: Customer) {
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
            it.insert(Customer.TABLE_NAME, null, values)
        }
        dbHelper.closeConnection()
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
                        id = c.getInt(c.getColumnIndexOrThrow(Customer.COLUMN_NAME_ID)),
                        name = c.getString(c.getColumnIndexOrThrow(Customer.COLUMN_NAME_NAME)),
                        lastName = c.getString(c.getColumnIndexOrThrow(Customer.COLUMN_NAME_LAST_NAME)),
                        email = c.getString(c.getColumnIndexOrThrow(Customer.COLUMN_NAME_EMAIL)),
                        phone = c.getString(c.getColumnIndexOrThrow(Customer.COLUMN_NAME_PHONE)),
                        address = c.getString(c.getColumnIndexOrThrow(Customer.COLUMN_NAME_ADDRESS))
                    )
                    customers.add(customer)
                }
            }
        }
        dbHelper.closeConnection()
        return customers
    }

    override fun getById(id: Int): Customer? {
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
                        id = c.getInt(c.getColumnIndexOrThrow(Customer.COLUMN_NAME_ID)),
                        name = c.getString(c.getColumnIndexOrThrow(Customer.COLUMN_NAME_NAME)),
                        lastName = c.getString(c.getColumnIndexOrThrow(Customer.COLUMN_NAME_LAST_NAME)),
                        email = c.getString(c.getColumnIndexOrThrow(Customer.COLUMN_NAME_EMAIL)),
                        phone = c.getString(c.getColumnIndexOrThrow(Customer.COLUMN_NAME_PHONE)),
                        address = c.getString(c.getColumnIndexOrThrow(Customer.COLUMN_NAME_ADDRESS))
                    )
                }
            }
        }
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
        dbHelper.closeConnection()
    }

    override fun delete(id: Int) {
        TODO("Not yet implemented")
    }
}