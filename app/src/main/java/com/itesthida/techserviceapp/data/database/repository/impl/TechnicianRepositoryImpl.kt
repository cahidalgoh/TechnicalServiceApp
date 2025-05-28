package com.itesthida.techserviceapp.data.database.repository.impl

import android.content.Context
import com.itesthida.techserviceapp.data.database.entities.Technician
import com.itesthida.techserviceapp.data.database.repository.TechnicianRepository

class TechnicianRepositoryImpl(private val context: Context) : TechnicianRepository {
    override fun insert(technician: Technician) {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Technician> {
        TODO("Not yet implemented")
    }

    override fun getById(id: Int): Technician? {
        TODO("Not yet implemented")
    }

    override fun update(technician: Technician) {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int) {
        TODO("Not yet implemented")
    }
}