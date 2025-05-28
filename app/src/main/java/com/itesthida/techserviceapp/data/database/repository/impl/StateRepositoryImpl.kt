package com.itesthida.techserviceapp.data.database.repository.impl

import android.content.Context
import com.itesthida.techserviceapp.data.database.entities.State
import com.itesthida.techserviceapp.data.database.repository.StateRepository

class StateRepositoryImpl(private val context: Context) : StateRepository {
    override fun insert(state: State) {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<State> {
        TODO("Not yet implemented")
    }

    override fun getById(id: Int): State? {
        TODO("Not yet implemented")
    }

    override fun update(state: State) {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int) {
        TODO("Not yet implemented")
    }
}