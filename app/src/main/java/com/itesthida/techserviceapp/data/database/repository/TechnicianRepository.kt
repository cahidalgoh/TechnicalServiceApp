package com.itesthida.techserviceapp.data.database.repository

import com.itesthida.techserviceapp.data.database.entities.Technician

interface TechnicianRepository {
    fun insert(technician: Technician)
    fun getAll(): List<Technician>
    fun getById(id: Int): Technician?
    fun update(technician: Technician)
    fun delete(id: Int)
    fun login(email: String, password: String): Technician?
    fun getByEmail(email: String): Technician?
}