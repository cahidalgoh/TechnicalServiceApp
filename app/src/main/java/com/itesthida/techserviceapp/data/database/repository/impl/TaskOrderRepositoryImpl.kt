package com.itesthida.techserviceapp.data.database.repository.impl

import android.content.Context
import com.itesthida.techserviceapp.data.database.entities.TaskOrder
import com.itesthida.techserviceapp.data.database.repository.TaskOrderRepository

class TaskOrderRepositoryImpl(private val context: Context) : TaskOrderRepository {
    override fun insert(taskOrder: TaskOrder) {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<TaskOrder> {
        TODO("Not yet implemented")
    }

    override fun getById(id: Int): TaskOrder? {
        TODO("Not yet implemented")
    }

    override fun update(taskOrder: TaskOrder) {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int) {
        TODO("Not yet implemented")
    }
}