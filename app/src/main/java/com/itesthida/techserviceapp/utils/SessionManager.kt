package com.itesthida.techserviceapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.ContextCompat
import com.itesthida.techserviceapp.R

class SessionManager (context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)

    fun saveSession(id: Int, name: String){
        // Guardamos los datos
        preferences.edit().apply {
            putInt("TECHNICIAN_ID", id)
            putString("TECHNICIAN_NAME", name)
            apply()
        }
    }

    /**
     * Devuelve el id del técnico que inició sesión en la aplicación, si no hay usuario logueado, devuelve -1.
     */
    fun getTechnicianId(): Int = preferences.getInt("TECHNICIAN_ID", -1)

    /**
     * Devuelve el nombre del técnico que inició sesión en la aplicación, si no hay usuario logueado, devuelve "Technician"
     */
    fun getTechnicianName(context: Context): String? = preferences.getString("TECHNICIAN_NAME", ContextCompat.getString(context, R.string.constant_technician_name))

    /**
     * Elimina las variables de sesión
     */
    fun logout(){
        preferences.edit().clear().apply()
    }
}