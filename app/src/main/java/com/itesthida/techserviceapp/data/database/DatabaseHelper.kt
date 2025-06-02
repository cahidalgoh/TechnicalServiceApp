package com.itesthida.techserviceapp.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.itesthida.techserviceapp.R
import com.itesthida.techserviceapp.data.database.entities.Customer
import com.itesthida.techserviceapp.data.database.entities.Equipment
import com.itesthida.techserviceapp.data.database.entities.EquipmentType
import com.itesthida.techserviceapp.data.database.entities.State
import com.itesthida.techserviceapp.data.database.entities.TaskOrder
import com.itesthida.techserviceapp.data.database.entities.Technician

/**
 * Clase con constructor privado, para limitar la capacidad de crear instancias de
 * la clase a la propia clase o a su objeto complementario (companion object).
 * Esto permite una creación controlada de objetos.
 * Por eso, se declara como private constructor para que la instancia de
 * DatabaseHelper solo pueda ser creada desde dentro de la propia clase.
 *
 * SQLiteOpenHelper es una clase base de Android que facilita la gestión de bases de datos SQLite.
 * El DatabaseHelper extiende de ella para implementar las operaciones que facilitan
 * la creación, actualización y manejo de las bases de datos.
 *
 * El constructor privado toma un context (necesario para SQLiteOpenHelper), pero usa
 * context.applicationContext para evitar fugas de memoria debido al ciclo de vida de las actividades.
 */
class DatabaseHelper private constructor(private val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        private var database: SQLiteDatabase? = null
    /*
     * companion object se usa en Kotlin para declarar miembros estáticos dentro de la clase.
     * Los miembros dentro de un companion object pueden ser accedidos sin crear una instancia de la clase.
     */
    companion object {
        /*
         * En este bloque, se definen dos constantes: el nombre de la base de datos y la versión de la base de datos.
         * Si cambia el esquema de la base de datos, debe incrementar la versión de la base de datos
         */
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "technical_service.db"

        /*
         * Constante para la creación de la tabla TECHNICIANS
         * Aquí se está creando una cadena SQL para crear la tabla Technician.
         * Las variables de la clase Technician se interpolan dentro de la cadena SQL para especificar
         * el nombre de la tabla y los nombres de las columnas.
         * Es importante notar que se está utilizando Technician.TABLE_NAME, Technician.COLUMN_NAME_ID, etc.,
         * para hacer el código más reutilizable y evitar hardcoding.
         */
        private const val SQL_CREATE_TECHNICIANS =
            "CREATE TABLE ${Technician.TABLE_NAME} (" +
                    "${Technician.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "${Technician.COLUMN_NAME_NAME} TEXT, " +
                    "${Technician.COLUMN_NAME_LAST_NAME} TEXT, " +
                    "${Technician.COLUMN_NAME_EMAIL} TEXT, " +
                    "${Technician.COLUMN_NAME_PASSWORD} TEXT)"
        /*
         * Constante para la eliminación de la tabla TECHNICIANS
         * Define el SQL para eliminar la tabla Technician si ya existe.
         * Esto se utilizará en la actualización de la base de datos.
         */
        private const val SQL_DELETE_TECHNICIANS = "DROP TABLE IF EXISTS ${Technician.TABLE_NAME}"


        // Constante para la creación de la tabla CUSTOMERS
        private const val SQL_CREATE_CUSTOMERS =
            "CREATE TABLE ${Customer.TABLE_NAME} (" +
                    "${Customer.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "${Customer.COLUMN_NAME_NAME} TEXT, " +
                    "${Customer.COLUMN_NAME_LAST_NAME} TEXT, " +
                    "${Customer.COLUMN_NAME_EMAIL} TEXT, " +
                    "${Customer.COLUMN_NAME_PHONE} TEXT, " +
                    "${Customer.COLUMN_NAME_ADDRESS} TEXT)"
        // Constante para la eliminación de la tabla CUSTOMERS
        private const val SQL_DELETE_CUSTOMERS = "DROP TABLE IF EXISTS ${Customer.TABLE_NAME}"


        // Constante para la creación de la tabla EQUIPMENT_TYPES
        private const val SQL_CREATE_EQUIPMENT_TYPES =
            "CREATE TABLE ${EquipmentType.TABLE_NAME} (" +
                    "${EquipmentType.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "${EquipmentType.COLUMN_NAME_NAME} TEXT, " +
                    "${EquipmentType.COLUMN_NAME_BRAND} TEXT, " +
                    "${EquipmentType.COLUMN_NAME_MODEL} TEXT)"
        // Constante para la eliminación de la tabla EQUIPMENT_TYPES
        private const val SQL_DELETE_EQUIPMENT_TYPES = "DROP TABLE IF EXISTS ${EquipmentType.TABLE_NAME}"


        // Constante para la creación de la tabla STATES
        private const val SQL_CREATE_STATES =
            "CREATE TABLE ${State.TABLE_NAME} (" +
                    "${State.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "${State.COLUMN_NAME_STATE_NAME} TEXT)"
        // Constante para la eliminación de la tabla CUSTOMERS
        private const val SQL_DELETE_STATES = "DROP TABLE IF EXISTS ${State.TABLE_NAME}"


        // Constante para la creación de la tabla EQUIPMENTS
        private const val SQL_CREATE_EQUIPMENT =
            "CREATE TABLE ${Equipment.TABLE_NAME} (" +
                    "${Equipment.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "${Equipment.COLUMN_NAME_CUSTOMER} INTEGER, " +
                    "${Equipment.COLUMN_NAME_EQUIPMENT_TYPE} INTEGER, " +
                    "${Equipment.COLUMN_NAME_SERIAL_NUMBER} TEXT)"
        // Constante para la eliminación de la tabla EQUIPMENTS
        private const val SQL_DELETE_EQUIPMENT = "DROP TABLE IF EXISTS ${Equipment.TABLE_NAME}"


        // Constante para la creación de la tabla TASK_ORDERS
        private const val SQL_CREATE_TASK_ORDERS =
            "CREATE TABLE ${TaskOrder.TABLE_NAME} (" +
                    "${TaskOrder.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "${TaskOrder.COLUMN_NAME_TECHNICIAN} INTEGER, " +
                    "${TaskOrder.COLUMN_NAME_CUSTOMER} INTEGER, " +
                    "${TaskOrder.COLUMN_NAME_EQUIPMENT} INTEGER, " +
                    "${TaskOrder.COLUMN_NAME_DESCRIPTION} TEXT, " +
                    "${TaskOrder.COLUMN_NAME_DATE} INTEGER, " +
                    "${TaskOrder.COLUMN_NAME_STATE} INTEGER)"
        // Constante para la eliminación de la tabla TASK_ORDERS
        private const val SQL_DELETE_TASK_ORDERS = "DROP TABLE IF EXISTS ${TaskOrder.TABLE_NAME}"


        /*
         * @Volatile asegura que la variable instance sea visible de forma inmediata a todos los hilos.
         * Esencialmente, evita la caché de la variable en cada hilo, asegurando que siempre se vean los cambios de inmediato.
         * instance es la variable donde se almacenará la única instancia de DatabaseHelper.
         */
        @Volatile
        private var instance : DatabaseHelper? = null
        /*
         * 'getInstance' -> Esta función es el núcleo del patrón Singleton. La idea es que se garantiza que solo
         * se creará una instancia de DatabaseHelper, incluso si se llama varias veces desde diferentes hilos.
         * El código usa el operador Elvis (?:) para asegurarse de que instance solo se cree una vez
         * y después se reutilice. Si instance ya tiene un valor (no es null), se devuelve esa instancia.
         */
        fun getInstance(context: Context): DatabaseHelper{
            /*
             * Primero, instance ?: synchronized(this): Aquí se verifica si instance es null. Si no lo es,
             * devuelve la instancia ya existente. Si es null, entra al bloque sincronizado para evitar
             * que varios hilos creen la instancia al mismo tiempo.
             * synchronized(this): El bloque synchronized garantiza que solo un hilo a
             * la vez pueda ejecutar el código dentro de este bloque. Esto es crucial para
             * evitar que múltiples hilos creen múltiples instancias de DatabaseHelper.
             */
            return instance ?: synchronized(this) {
                /*
                 * Dentro del bloque sincronizado, se vuelve a verificar si instance es null
                 * porque otro hilo podría haber creado la instancia mientras el primer
                 * hilo estaba esperando para entrar al bloque sincronizado.
                 * Si sigue siendo null, entonces se crea una nueva instancia de DatabaseHelper y se
                 * asigna a instance usando el méto-do also { instance = it }.
                 * also es una función de extensión en Kotlin que permite realizar una acción sobre un
                 * objeto (en este caso, asignar la nueva instancia a instance) y luego devolver el mismo objeto.
                 */
                instance ?: DatabaseHelper(context).also { instance = it }
            }
            /*
             * En resumen, la función getInstance:
             * 1- Comprueba si instance ya ha sido creada.
             * 2- Si no, entra en un bloque sincronizado para garantizar que solo un hilo cree la instancia.
             * 3- Si instance sigue siendo null dentro del bloque sincronizado, se crea la instancia y se asigna a instance.
             */
        }

    }

    /**
     * Este méto-do es parte de la clase SQLiteOpenHelper y se ejecuta cuando la base de datos es creada por primera vez.
     * Aquí se ejecutan las consultas SQL definidas en SQL_CREATE_TECHNICIAN, etc para crear las tablas
     */
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TECHNICIANS)
        db.execSQL(SQL_CREATE_CUSTOMERS)
        db.execSQL(SQL_CREATE_EQUIPMENT_TYPES)
        db.execSQL(SQL_CREATE_STATES)
        db.execSQL(SQL_CREATE_EQUIPMENT)
        db.execSQL(SQL_CREATE_TASK_ORDERS)

        // Insertamos los estados iniciales
        insertInitialStates(context, db)
    }

    /**
     * Este méto-do se ejecuta cuando la base de datos necesita ser actualizada (por ejemplo, si la versión de la base de datos cambia).
     * Aquí se eliminan las tablas creada con onDestroy(db) (con SQL_DELETE_XXXXXXXX ...) y luego se vuelve a crear las tablas con onCreate(db).
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onDestroy(db)
        onCreate(db)
    }

    /**
     * Función que se llama cuando existe una modificación en la base de datos
     *
     * @param db The database.
     */
    fun onDestroy(db: SQLiteDatabase) {
        db.execSQL(SQL_DELETE_TASK_ORDERS)
        db.execSQL(SQL_DELETE_EQUIPMENT)
        db.execSQL(SQL_DELETE_STATES)
        db.execSQL(SQL_DELETE_EQUIPMENT_TYPES)
        db.execSQL(SQL_DELETE_CUSTOMERS)
        db.execSQL(SQL_DELETE_TECHNICIANS)
    }

    /**
     * Abre la conexión a la base de datos (modo escritura)
     */
    fun openConnection() {
        if (database == null || !database!!.isOpen) {
            database = writableDatabase
        }
    }

    /**
     * Cierra la conexión a la base de datos
     */
    fun closeConnection() {
        if (database != null && database!!.isOpen) {
            database!!.close()
        }
    }

    /**
     * Opción para exponer el objeto SQLiteDatabase si se necesita
     */
    fun getDatabase(): SQLiteDatabase? {
        return database
    }

    private fun insertInitialStates(context: Context, db: SQLiteDatabase) {
        val states = listOf(
            context.getString(R.string.state_pending),
            context.getString(R.string.state_progress),
            context.getString(R.string.state_completed),
            context.getString(R.string.state_cancelled)
        )
        for (state in states) {
            db.execSQL("INSERT INTO ${State.TABLE_NAME} (${State.COLUMN_NAME_STATE_NAME}) VALUES ('$state')")
        }
    }
}