package com.itesthida.techserviceapp.data.database.repository

import com.itesthida.techserviceapp.data.database.entities.Equipment

interface EquipmentRepository {
    fun insert(equipment: Equipment)
    fun getAll(): List<Equipment>
    fun getById(id: Int): Equipment?
    fun update(equipment: Equipment)
    fun delete(id: Int)

}