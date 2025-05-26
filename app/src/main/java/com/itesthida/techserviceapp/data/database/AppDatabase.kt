package com.itesthida.techserviceapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.itesthida.techserviceapp.data.database.dao.TechnicianDAO
import com.itesthida.techserviceapp.data.database.entities.Technician

@Database(
    entities = [Technician::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun technicianDAO(): TechnicianDAO

    companion object{
        @Volatile
        private var INSTANCE : AppDatabase? = null

        /**
         * Crea una única instancia de la base de datos si no existe.
         * Devuelve siempre la misma instancia para toda la app.
         * Usa Room.databaseBuilder para construir la base de datos con el contexto de la app.
         */
        fun getDatabase(context: Context): AppDatabase{
            // Verifica si existe una instancia de la base de datos
            return INSTANCE ?: synchronized(this){
                // No existe instancia, la construye haciendo uso de Room.databaseBuilder
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "technical_service_db" // Crea la instancia con este nombre
                ).build()
                // La guarda en la variable INSTANCE
                INSTANCE = instance
                instance
            }
        }
    }
}