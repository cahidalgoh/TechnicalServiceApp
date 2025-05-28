package com.itesthida.techserviceapp.data.database.repository

import com.itesthida.techserviceapp.data.database.entities.Customer

interface CustomerRepository {
    fun insert(customer: Customer)
    fun getAll(): List<Customer>
    fun getById(id: Int): Customer?
    fun update(customer: Customer)
    fun delete(id: Int)
}