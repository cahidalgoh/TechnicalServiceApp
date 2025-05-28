package com.itesthida.techserviceapp.data.database.repository.impl

import android.content.Context
import com.itesthida.techserviceapp.data.database.entities.Equipment
import com.itesthida.techserviceapp.data.database.repository.EquipmentRepository

class EquipmentRepositoryImpl(private val context: Context) : EquipmentRepository {
    override fun insert(equipment: Equipment) {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Equipment> {
        TODO("Not yet implemented")
    }

    override fun getById(id: Int): Equipment? {
        TODO("Not yet implemented")
    }

    override fun update(equipment: Equipment) {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int) {
        TODO("Not yet implemented")
    }
}