package com.itesthida.techserviceapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.ContextCompat
import com.itesthida.techserviceapp.R

class SessionManager (context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("TechServicePrefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_TECHNICIAN_ID = "TECHNICIAN_ID"
        private const val KEY_TECHNICIAN_NAME = "TECHNICIAN_NAME"
        private const val IS_LOGGED_IN = "IS_LOGGED_IN"
        private const val DEFAULT_TECHNICIAN_ID = -1
        private const val DEFAULT_TECHNICIAN_NAME = "Technician"
    }

    fun saveSession(id: Int, name: String){
        // Guardamos los datos
        preferences.edit().apply {
            putInt(KEY_TECHNICIAN_ID, id)
            putString(KEY_TECHNICIAN_NAME, name)
            putBoolean(IS_LOGGED_IN, true)
            apply()
        }
    }

    /**
     * Devuelve el id del técnico que inició sesión en la aplicación, si no hay usuario logueado, devuelve -1.
     */
    fun getTechnicianId(): Int = preferences.getInt(KEY_TECHNICIAN_ID, DEFAULT_TECHNICIAN_ID)

    /**
     * Devuelve el nombre del técnico que inició sesión en la aplicación, si no hay usuario logueado, devuelve "Technician"
     */
    fun getTechnicianName(): String = preferences.getString(KEY_TECHNICIAN_NAME, DEFAULT_TECHNICIAN_NAME) ?: DEFAULT_TECHNICIAN_NAME

    /**
     * Devuelve Verdadero o falso si el id del técnico es distinto del identificador por defecto.
     */
    fun isLoggedIn(): Boolean = preferences.getBoolean(IS_LOGGED_IN, false)

    /**
     * Elimina las variables de sesión
     */
    fun logout(){
        preferences.edit().clear().apply()
    }
}