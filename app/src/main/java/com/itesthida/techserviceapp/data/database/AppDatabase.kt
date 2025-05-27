package com.itesthida.techserviceapp.data.database

//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import com.itesthida.techserviceapp.data.database.dao.TechnicianDAO
//import com.itesthida.techserviceapp.data.database.entities.Technician

abstract class AppDatabase{

}/*
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
        */

/*

 1  import android.content.Context
 2  import android.database.sqlite.SQLiteDatabase
 3  import android.database.sqlite.SQLiteOpenHelper
 4
 5  class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
 6
 7      companion object {
 8          private const val DATABASE_NAME = "technicians.db"
 9          private const val DATABASE_VERSION = 1
10
11          // Nombre de la tabla de la base de datos
12          private const val TABLE_NAME = "technicians"
13
14          // Definir columnas de la tabla
15          private const val COLUMN_ID = "id"
16          private const val COLUMN_NAME = "name"
17          private const val COLUMN_LAST_NAME = "lastName"
18          private const val COLUMN_EMAIL = "email"
19          private const val COLUMN_PASSWORD = "password"
20      }
21
22      override fun onCreate(db: SQLiteDatabase?) {
23          val CREATE_TABLE_QUERY = """
24              CREATE TABLE $TABLE_NAME (
25                  $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
26                  $COLUMN_NAME TEXT,
27                  $COLUMN_LAST_NAME TEXT,
28                  $COLUMN_EMAIL TEXT,
29                  $COLUMN_PASSWORD TEXT
30              )
31          """
32          db?.execSQL(CREATE_TABLE_QUERY)
33      }
34
35      override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
36          db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
37          onCreate(db)
38      }
39
40      // Méto do para abrir la base de datos (puedes usar getWritableDatabase o getReadableDatabase)
41      fun openDatabase(): SQLiteDatabase {
42          return writableDatabase
43      }
44
45      // Méto do para cerrar la base de datos
46      fun closeDatabase() {
47          close()
48      }
49  }

*/