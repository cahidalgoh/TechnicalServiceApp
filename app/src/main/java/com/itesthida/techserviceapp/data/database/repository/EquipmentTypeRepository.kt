package com.itesthida.techserviceapp.data.database.repository

import com.itesthida.techserviceapp.data.database.entities.EquipmentType

interface EquipmentTypeRepository {
    fun insert(technician: EquipmentType)
    fun getAll(): List<EquipmentType>
    fun getById(id: Int): EquipmentType?
    fun update(technician: EquipmentType)
    fun delete(id: Int)
}