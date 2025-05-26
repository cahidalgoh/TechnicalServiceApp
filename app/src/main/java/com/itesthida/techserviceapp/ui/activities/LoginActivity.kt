package com.itesthida.techserviceapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.itesthida.techserviceapp.R
import com.itesthida.techserviceapp.data.database.AppDatabase
import com.itesthida.techserviceapp.databinding.ActivityLoginBinding
import com.itesthida.techserviceapp.utils.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    // Declaración de los objetos de la clase
    private lateinit var binding: ActivityLoginBinding
    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Inicialización de las variables y/o componentes
        initComponents()

        // Inicialización de los listeners
        initListeners()
    }

    /**
     * Inicialización de variables declaradas
     */
    private fun initComponents() {
        //Para el acceso a los componentes del layout
        // Inicializamos el binding a través de la clase que representa el layout del login
        // pasándole la propiedad layoutInflater que ya está en el Activity
        binding = ActivityLoginBinding.inflate(layoutInflater)

        // Para el acceso a los componentes del layout establecemos en la vista el activity desde el binding
        // inicializado con todas las referencias a los componentes que tengan un id en el layout
        setContentView(binding.root)

        // Inicializamos el SessionManager
        session = SessionManager(this)

        // Recuperamos los datos del inicio de session
        val username = binding.etUsername.text
        val password = binding.etPassword.text
    }

    /**
     * Inicializa los listeners
     */
    private fun initListeners() {
        // Listener para el botón login
        binding.btnLogin.setOnClickListener {
            // Obtenemos los datos introducidos por el usuario
            val email = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()){
                // Comprobamos las credenciales
                validateCredentials(email, password)
            } else{
                // Mensaje al usuario
                Toast.makeText(this, getString(R.string.login_incomplete_fields), Toast.LENGTH_SHORT).show()
            }
        }

        // Listener para el botón new technician
        binding.btnNewTechnician.setOnClickListener {
            // Creamos el intent para navegar a la pantalla que permite el registro de un nuevo técnico
            val intent = Intent(this, NewTechnicianActivity::class.java)
            // Inicia el pase a la pantalla de registro
            startActivity(intent)
        }
    }



    /**
     * Valida las credenciales introducidas por el usuario
     */
    private fun validateCredentials(email: String, password: String) {

        if (email == "admin" && password == "admin"){
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            // Para que no vuelva a esta pantalla si hace uso del botón volver
            finish()
        } else{
            // Datos de login incorrectos
            // Mensaje al usuario
            Toast.makeText(this@LoginActivity, getString(R.string.login_error_message), Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Valida las credenciales introducidas por el usuario
     */
    private fun validateCredentialsRoom(email: String, password: String) {
        // Inicia una corrutina en segundo plano (Dispatchers.IO) para no bloquear la interfaz.
        /*
        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getDatabase(applicationContext)

            // Llama a un méthod login(...) del TechnicianDao
            val technician = database.technicianDAO().login(email, password)

            // Vuelve al hilo principal para pasar a otra pantalla si el login es correcto
            // o mostrar un mensaje al usuario si el usuario no está registrado
            runOnUiThread {
                if (technician != null){
                    session.saveSession(technician.id, technician.name)
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    // Para que no vuelva a esta pantalla si hace uso del botón volver
                    finish()
                } else{
                    // Datos de login incorrectos
                    // Mensaje al usuario
                    Toast.makeText(this@LoginActivity, getString(R.string.login_error_message), Toast.LENGTH_SHORT).show()
                }
            }
        }
        */

    }
}
/*


uedes explicarme que hace el getDatabase?

package com.example.servicetechapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.servicetechapp.data.database.dao.TecnicoDao
import com.example.servicetechapp.data.database.entities.Tecnico

@Database(
    entities = [
        Tecnico::class,
        // Agrega aquí otras entidades: Cliente, Orden, Repuesto, etc.
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tecnicoDao(): TecnicoDao
    // Otros DAOs aquí...

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "service_tech_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

ChatGPT Plus
Claro, te explico qué hace la función getDatabase(context: Context) dentro del archivo que muestras.

Contexto general
Este código define una base de datos local usando Room, que es la librería de persistencia de datos recomendada por Android. La clase AppDatabase es una subclase de RoomDatabase, y contiene los DAOs (Data Access Objects) que permiten acceder a las entidades, como Tecnico.

¿Qué hace getDatabase(context)?
La función getDatabase(context: Context): AppDatabase es un singleton, lo que significa que se asegura de que solo exista una única instancia de la base de datos en toda la app. Esto es importante para evitar errores de concurrencia, fugas de memoria o acceso inconsistente a los datos.

Paso a paso:
Verifica si ya existe una instancia de la base de datos (INSTANCE):

kotlin
Copiar
Editar
return INSTANCE ?: synchronized(this) {
Si no existe, entra en un bloque sincronizado (para evitar que múltiples hilos creen instancias al mismo tiempo):

kotlin
Copiar
Editar
synchronized(this) {
    val instance = Room.databaseBuilder(...)
Construye la base de datos usando Room.databaseBuilder:

kotlin
Copiar
Editar
Room.databaseBuilder(
    context.applicationContext,
    AppDatabase::class.java,
    "service_tech_db"
).build()
Esto crea una base de datos con el nombre "service_tech_db" y asociada al contexto de aplicación.

Guarda la instancia en la variable INSTANCE para futuras llamadas.

Devuelve la instancia para que el resto de la app pueda usarla.

¿Por qué es importante?
Evita múltiples instancias que podrían causar pérdida o corrupción de datos.

Centraliza el acceso a la base de datos desde un único punto.

Es más eficiente que crear la base de datos cada vez que se necesita acceder a ella.

En resumen
La función getDatabase(context):

✅ Crea una única instancia de la base de datos si no existe.
✅ Devuelve siempre la misma instancia para toda la app.
✅ Usa Room.databaseBuilder para construir la base de datos con el contexto de la app.

¿Quieres que te muestre cómo usar esta base de datos desde una ViewModel o un Repository?



Tú dijiste:
Claro que sí

ChatGPT Plus
¡Perfecto! Vamos a ver cómo puedes usar tu base de datos AppDatabase desde una ViewModel y un Repository, que es la forma recomendada por la arquitectura de Android (MVVM).

🧱 Arquitectura MVVM: Estructura Básica
Room: Provee acceso a la base de datos (a través del TecnicoDao)

Repository: Capa intermedia que abstrae el acceso a datos (de Room o red)

ViewModel: Provee datos a la UI y mantiene el estado durante cambios de configuración

1. 🧩 Repository (TecnicoRepository.kt)
kotlin
Copiar
Editar
package com.example.servicetechapp.data.repository

import androidx.lifecycle.LiveData
import com.example.servicetechapp.data.database.dao.TecnicoDao
import com.example.servicetechapp.data.database.entities.Tecnico

class TecnicoRepository(private val tecnicoDao: TecnicoDao) {

    val tecnicos: LiveData<List<Tecnico>> = tecnicoDao.getAllTecnicos()

    suspend fun insert(tecnico: Tecnico) {
        tecnicoDao.insert(tecnico)
    }

    suspend fun delete(tecnico: Tecnico) {
        tecnicoDao.delete(tecnico)
    }

    // Puedes agregar más operaciones como update, search, etc.
}
2. 🧠 ViewModel (TecnicoViewModel.kt)
kotlin
Copiar
Editar
package com.example.servicetechapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.servicetechapp.data.database.entities.Tecnico
import com.example.servicetechapp.data.repository.TecnicoRepository
import kotlinx.coroutines.launch

class TecnicoViewModel(private val repository: TecnicoRepository) : ViewModel() {

    val tecnicos: LiveData<List<Tecnico>> = repository.tecnicos

    fun insert(tecnico: Tecnico) {
        viewModelScope.launch {
            repository.insert(tecnico)
        }
    }

    fun delete(tecnico: Tecnico) {
        viewModelScope.launch {
            repository.delete(tecnico)
        }
    }
}
3. 🏭 ViewModelFactory
kotlin
Copiar
Editar
class TecnicoViewModelFactory(private val repository: TecnicoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TecnicoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TecnicoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
4. 🧩 En tu Activity o Fragment
kotlin
Copiar
Editar
val database = AppDatabase.getDatabase(applicationContext)
val tecnicoDao = database.tecnicoDao()
val repository = TecnicoRepository(tecnicoDao)
val viewModelFactory = TecnicoViewModelFactory(repository)
val tecnicoViewModel = ViewModelProvider(this, viewModelFactory)[TecnicoViewModel::class.java]

// Observar los datos
tecnicoViewModel.tecnicos.observe(this) { lista ->
    // Actualizar UI con la lista
}

// Insertar un nuevo técnico
tecnicoViewModel.insert(Tecnico(id = 0, nombre = "Juan Pérez"))
✅ ¿Qué necesitas tener en tu TecnicoDao?
kotlin
Copiar
Editar
@Dao
interface TecnicoDao {

    @Query("SELECT * FROM tecnico")
    fun getAllTecnicos(): LiveData<List<Tecnico>>

    @Insert
    suspend fun insert(tecnico: Tecnico)

    @Delete
    suspend fun delete(tecnico: Tecnico)
}
¿Te gustaría que prepare una versión más simple para un ejemplo de prueba (como una app con solo una lista de técnicos)?


* */