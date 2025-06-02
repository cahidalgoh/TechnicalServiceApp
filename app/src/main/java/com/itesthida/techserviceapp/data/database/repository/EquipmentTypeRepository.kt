package com.itesthida.techserviceapp.data.database.repository

import com.itesthida.techserviceapp.data.database.entities.EquipmentType

interface EquipmentTypeRepository {
    fun insert(equipmentType: EquipmentType)
    fun getAll(): List<EquipmentType>
    fun getById(id: Int): EquipmentType?
    fun update(equipmentType: EquipmentType)
    fun delete(id: Int)
}