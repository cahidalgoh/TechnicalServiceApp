package com.itesthida.techserviceapp.data.database.repository

import com.itesthida.techserviceapp.data.database.entities.State

interface StateRepository {
    fun insert(state: State)
    fun getAll(): List<State>
    fun getById(id: Int): State?
    fun update(state: State)
    fun delete(id: Int)
}