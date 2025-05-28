package com.itesthida.techserviceapp.data.database.repository.impl

import android.content.Context
import com.itesthida.techserviceapp.data.database.entities.Customer
import com.itesthida.techserviceapp.data.database.repository.CustomerRepository

class CustomerRepositoryImpl(private val context: Context) : CustomerRepository {
    override fun insert(customer: Customer) {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Customer> {
        TODO("Not yet implemented")
    }

    override fun getById(id: Int): Customer? {
        TODO("Not yet implemented")
    }

    override fun update(customer: Customer) {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int) {
        TODO("Not yet implemented")
    }
}