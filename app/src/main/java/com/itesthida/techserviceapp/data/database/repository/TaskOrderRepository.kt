package com.itesthida.techserviceapp.data.database.repository

import com.itesthida.techserviceapp.data.database.entities.TaskOrder

interface TaskOrderRepository {
    fun insert(taskOrder: TaskOrder)
    fun getAll(): List<TaskOrder>
    fun getById(id: Int): TaskOrder?
    fun update(taskOrder: TaskOrder)
    fun delete(id: Int)
}