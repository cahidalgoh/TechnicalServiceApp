package com.itesthida.techserviceapp.utils

import android.util.Patterns
import java.security.MessageDigest//Importa la clase que permite usar funciones criptográficas de hash (como SHA-256, MD5, etc.).

/**
 * Clase utilitaria que contiene funciones reutilizables
 */
class TechServiceUtils {
    companion object{

        /**
         * Convierte una contraseña en un valor irreversible llamado hash.
         * Recibe una contraseña y devuelve su hash en formato hexadecimal
         */
        fun hashPassword(password: String): String{
            /*
            Esto se usa para almacenar contraseñas de forma segura.
            En lugar de guardar "123456", se guarda algo como "8d969eef6ecad3c29a3a629280e686cf...".

            SHA-256 es seguro para uso general y recomendado para hashear contraseñas, aunque en producción
            es mejor usar bcrypt, scrypt o Argon2 porque son más lentos y protegen contra ataques de fuerza bruta.
             */

            // Crea una instancia del algoritmo de hash SHA-256.
            val messageDigest = MessageDigest.getInstance("SHA-256")

            // Convierte la contraseña a bytes usando codificación UTF-8.
            // Calcula el hash: el resultado es un arreglo de bytes (ByteArray), no una cadena legible.
            val hashBytes = messageDigest.digest(password.toByteArray(Charsets.UTF_8))

            // Convierte el ByteArray resultante en una cadena hexadecimal legible (ej: "a2b1c3...").
            // %02x convierte cada byte a 2 dígitos hexadecimales.
            return hashBytes.joinToString("") { "%02x".format(it) }
        }

        fun validateEmail(email: String): Boolean{
            val emailPattern = Patterns.EMAIL_ADDRESS
            return emailPattern.matcher(email).matches()
            //https://www.youtube.com/watch?v=vwD00u6Lshw
        }

        fun validatePassword(password: String): Boolean{
            /*
                ^: Comienza de la cadena.
                (?!.*\\s): No debe contener espacios.
                (?=.*[a-z]): Debe tener al menos una letra minúscula.
                (?=.*[A-Z]): Debe tener al menos una letra mayúscula.
                (?=.*\\d): Debe tener al menos un número.
                (?=.*[^a-zA-Z0-9]): Debe tener al menos un carácter especial.
                .{8,}: La contraseña debe tener al menos 8 caracteres.
                $: Finaliza la cadena.
            * */
            val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$".toRegex()
            return passwordPattern.matches(password)



            /*

    *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
    *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
    *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*


            Para comprobar la contraseña durante el registro en Kotlin Android, puedes utilizar expresiones regulares para validar el formato de la contraseña y luego almacenar la contraseña encriptada en la base de datos. La validación de la contraseña en tiempo real (en la interfaz de usuario) y la encriptación (en el servidor) son medidas de seguridad cruciales.
    Validación de la contraseña en tiempo real (en la interfaz de usuario):
    Recopilar la contraseña: Utiliza un EditText para que el usuario ingrese la contraseña.
    Utilizar expresiones regulares: Define una expresión regular que establezca los requisitos mínimos para la contraseña (longitud mínima, letras mayúsculas, letras minúsculas, números, caracteres especiales, etc.).
    Mostrar la validación: Utiliza un TextView o cualquier otro componente para mostrar mensajes de validación al usuario. Puedes usar el TextInputLayout y sus métodos para mostrar mensajes de error.
    Ejemplo de validación con expresiones regulares (Kotlin):
    Kotlin

    import java.util.regex.Pattern

    fun validarContrasenia(contrasenia: String): Boolean {
        val patron = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$".toRegex()
        return patron.matches(contrasenia)
    }
    Explicación de la expresión regular:
    ^: Comienza de la cadena.
    (?!.*\\s): No debe contener espacios.
    (?=.*[a-z]): Debe tener al menos una letra minúscula.
    (?=.*[A-Z]): Debe tener al menos una letra mayúscula.
    (?=.*\\d): Debe tener al menos un número.
    (?=.*[^a-zA-Z0-9]): Debe tener al menos un carácter especial.
    .{8,}: La contraseña debe tener al menos 8 caracteres.
    $: Finaliza la cadena.
    Encriptación de la contraseña en el servidor (importante):
    No almacenes la contraseña en texto plano. Siempre encripta la contraseña antes de almacenarla en la base de datos.
    Usa un algoritmo de encriptación fuerte, como bcrypt o Argon2.
    Guarda la clave de encriptación (salt) junto con la contraseña encriptada.
    Recomendación: Utiliza un framework de autenticación como Firebase Authentication o implementa una autenticación personalizada con un backend en la nube.
    Validación en tiempo real en la interfaz de usuario (ejemplo con TextInputLayout):
    Kotlin

    import com.google.android.material.textfield.TextInputLayout

    // En tu layout XML, utiliza TextInputLayout para el EditText de la contraseña

    val inputLayoutContrasenia: TextInputLayout = findViewById(R.id.inputLayoutContrasenia)

    fun validarContraseniaEnTiempoReal(contrasenia: String) {
        if (validarContrasenia(contrasenia)) {
            inputLayoutContrasenia.error = null // Limpia el error
            inputLayoutContrasenia.isErrorEnabled = false // Desactiva el mensaje de error
        } else {
            inputLayoutContrasenia.error = "Contraseña no válida" // Muestra un mensaje de error
            inputLayoutContrasenia.isErrorEnabled = true // Activa el mensaje de error
        }
    }
    En resumen:
    Validación en la interfaz de usuario: Usa expresiones regulares y TextInputLayout para mostrar mensajes de error al usuario si la contraseña no cumple con los requisitos.
    Encriptación en el servidor: Siempre encripta la contraseña antes de almacenarla en la base de datos.
    Autenticación segura: Utiliza un framework de autenticación o implementa tu propia autenticación con un backend seguro.


    *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
    *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
    *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*




             */


        }

    }
}
/*

Puedes explicarme que hace el getDatabase?

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



Tú dijiste:
Puedes explicarme esta clase ?

class LoginActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        btnLogin = findViewById(R.id.btnLogin)
        sessionManager = SessionManager(this)

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                validarCredenciales(email, password)
            } else {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validarCredenciales(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getDatabase(applicationContext)
            val tecnico = db.tecnicoDao().login(email, password)

            runOnUiThread {
                if (tecnico != null) {
                    sessionManager.guardarSesion(tecnico.id, tecnico.nombre)
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

ChatGPT Plus
Claro, te explico paso a paso qué hace esta clase LoginActivity en tu app Android. Este es un típico ejemplo de una pantalla de inicio de sesión.

🧩 Clase: LoginActivity : AppCompatActivity()
✅ Propósito:
Manejar la pantalla de inicio de sesión de la aplicación.

🔍 ¿Qué contiene?
kotlin
Copiar
Editar
private lateinit var edtEmail: EditText
private lateinit var edtPassword: EditText
private lateinit var btnLogin: Button
private lateinit var sessionManager: SessionManager
Estas variables hacen referencia a los componentes visuales (UI) que vienen del layout XML (activity_login.xml):

edtEmail: Campo de texto para el email

edtPassword: Campo de texto para la contraseña

btnLogin: Botón para iniciar sesión

sessionManager: Clase para guardar la sesión del usuario (probablemente en SharedPreferences)

🔁 onCreate(): Se llama cuando se crea la pantalla
kotlin
Copiar
Editar
setContentView(R.layout.activity_login)
Esto asocia la clase con el layout XML de la interfaz (activity_login.xml).

Luego busca los elementos del layout con findViewById():

kotlin
Copiar
Editar
edtEmail = findViewById(R.id.edtEmail)
edtPassword = findViewById(R.id.edtPassword)
btnLogin = findViewById(R.id.btnLogin)
sessionManager = SessionManager(this)
🧠 Lógica del botón de login
kotlin
Copiar
Editar
btnLogin.setOnClickListener {
    val email = edtEmail.text.toString().trim()
    val password = edtPassword.text.toString().trim()

    if (email.isNotEmpty() && password.isNotEmpty()) {
        validarCredenciales(email, password)
    } else {
        Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
    }
}
Cuando el usuario presiona el botón "Login":

Obtiene los valores de los campos email y password.

Si ambos están completos, llama a validarCredenciales(...)

Si falta alguno, muestra un Toast diciendo que se deben completar todos los campos.

🔐 Método: validarCredenciales(email, password)
Este método hace lo siguiente:

kotlin
Copiar
Editar
CoroutineScope(Dispatchers.IO).launch {
    val db = AppDatabase.getDatabase(applicationContext)
    val tecnico = db.tecnicoDao().login(email, password)
Inicia una corrutina en segundo plano (Dispatchers.IO) para no bloquear la interfaz.

Obtiene una instancia de la base de datos Room.

Llama a un método login(...) del TecnicoDao, que seguramente hace una consulta SQL del tipo:

sql
Copiar
Editar
SELECT * FROM tecnico WHERE email = :email AND password = :password
🧾 Resultado del login
kotlin
Copiar
Editar
runOnUiThread {
    if (tecnico != null) {
        sessionManager.guardarSesion(tecnico.id, tecnico.nombre)
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    } else {
        Toast.makeText(this@LoginActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
    }
}
Si encuentra un técnico (es decir, login() devolvió un resultado), guarda los datos del técnico en la sesión (SharedPreferences) y abre la siguiente pantalla (MainActivity).

Si no lo encuentra, muestra un mensaje de error: "Credenciales incorrectas".

📦 Resumen
Elemento	Función
LoginActivity	Pantalla de login
SessionManager	Guarda sesión del usuario
validarCredenciales()	Verifica credenciales en la base de datos
btnLogin.setOnClickListener	Dispara el login al presionar el botón
AppDatabase.getDatabase	Crea o recupera la instancia singleton de la base de datos Room

¿Quieres que te muestre cómo podría ser el método login() dentro del TecnicoDao?



Tú dijiste:
antes, qué significa runOnUiThread

ChatGPT Plus
Buena pregunta. runOnUiThread es una función que te permite ejecutar código en el hilo principal de la aplicación (también llamado UI thread, o hilo de la interfaz de usuario).

💡 ¿Por qué es necesario?
En Android, solo el hilo principal puede modificar la interfaz de usuario. Si intentas actualizar la UI (por ejemplo, mostrar un Toast, cambiar un TextView, navegar a otra Activity, etc.) desde otro hilo (como una corrutina en Dispatchers.IO), la app lanzará una excepción (CalledFromWrongThreadException).

📌 ¿Qué hace exactamente?
kotlin
Copiar
Editar
runOnUiThread {
    // Código que modifica la UI
}
Esto asegura que el bloque de código dentro del runOnUiThread se ejecute en el hilo principal, sin importar en qué hilo estás actualmente.

🧠 Ejemplo contextualizado con tu código:
kotlin
Copiar
Editar
CoroutineScope(Dispatchers.IO).launch {
    val tecnico = db.tecnicoDao().login(email, password)

    runOnUiThread {
        if (tecnico != null) {
            // Actualizar la interfaz y navegar a otra pantalla
        } else {
            Toast.makeText(this@LoginActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
        }
    }
}
Dispatchers.IO ejecuta el código en un hilo de fondo para hacer operaciones pesadas (como acceder a la base de datos).

Luego, runOnUiThread se usa para volver al hilo principal para mostrar mensajes o cambiar de pantalla.

✅ En resumen:
Función	Propósito
runOnUiThread {}	Ejecutar código en el hilo principal (UI)
¿Por qué usarlo?	Porque solo el hilo principal puede tocar la UI

¿Quieres que te muestre cómo hacer esto usando corrutinas modernas con viewModelScope y withContext(Dispatchers.Main) como alternativa?



Tú dijiste:
Claro

ChatGPT Plus
Perfecto. Vamos a ver cómo puedes usar corrutinas modernas (con viewModelScope y withContext(Dispatchers.Main)) para evitar runOnUiThread y escribir código más limpio, especialmente si estás usando el patrón MVVM.

🧠 ¿Por qué cambiar a viewModelScope y withContext?
Es más limpio y seguro.

Se recomienda para apps modernas con arquitectura MVVM.

Se evita mezclar lógica de UI y lógica de datos en la Activity.

✅ Reescribiendo validarCredenciales con viewModelScope
1. ViewModel con viewModelScope
Primero, crea o actualiza un LoginViewModel:

kotlin
Copiar
Editar
class LoginViewModel(private val repository: TecnicoRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    fun validarCredenciales(email: String, password: String) {
        viewModelScope.launch {
            val tecnico = repository.login(email, password)
            _loginResult.value = tecnico != null
        }
    }
}
2. Repository con función suspend
kotlin
Copiar
Editar
class TecnicoRepository(private val tecnicoDao: TecnicoDao) {

    suspend fun login(email: String, password: String): Tecnico? {
        return tecnicoDao.login(email, password)
    }
}
3. Activity observando el resultado
kotlin
Copiar
Editar
class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tecnicoDao = AppDatabase.getDatabase(applicationContext).tecnicoDao()
        val repository = TecnicoRepository(tecnicoDao)
        val factory = LoginViewModelFactory(repository)

        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
        sessionManager = SessionManager(this)

        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginViewModel.validarCredenciales(email, password)
            } else {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        loginViewModel.loginResult.observe(this) { success ->
            if (success) {
                // Idealmente recuperar el Técnico de otro LiveData
                sessionManager.guardarSesion(1, "Nombre") // Ejemplo
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
🧾 Ventajas de esta versión
Mejora	Descripción
✅ Separación de lógica	La lógica de datos está en el ViewModel/Repository
✅ Sin runOnUiThread	Usamos LiveData que ya funciona en el hilo principal
✅ Más testable	ViewModel y Repository pueden probarse sin UI
✅ Mejor arquitectura	Sigues MVVM, recomendado por Android Jetpack

¿Quieres que te prepare un proyecto de ejemplo mínimo con todo esto junto? ¿O prefieres ver cómo se implementa SessionManager también?



Tú dijiste:
Las 2 cosas

ChatGPT Plus
¡Por supuesto! A continuación, te presento un ejemplo completo de una aplicación Android utilizando MVVM, Room y un SessionManager para gestionar la sesión del usuario. Este enfoque es moderno, limpio y sigue las mejores prácticas recomendadas por Android.

🛠️ Estructura del Proyecto
La estructura básica del proyecto es la siguiente:

pgsql
Copiar
Editar
com.example.servicetechapp
├── data
│   ├── database
│   │   ├── AppDatabase.kt
│   │   ├── dao
│   │   │   └── TecnicoDao.kt
│   │   └── entities
│   │       └── Tecnico.kt
│   └── repository
│       └── TecnicoRepository.kt
├── ui
│   └── login
│       ├── LoginActivity.kt
│       └── LoginViewModel.kt
└── utils
    └── SessionManager.kt
📦 Paso 1: Definir la Entidad Tecnico
kotlin
Copiar
Editar
@Entity(tableName = "tecnico")
data class Tecnico(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val email: String,
    val password: String
)
🗂️ Paso 2: Crear el DAO TecnicoDao
kotlin
Copiar
Editar
@Dao
interface TecnicoDao {
    @Insert
    suspend fun insert(tecnico: Tecnico)

    @Query("SELECT * FROM tecnico WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): Tecnico?
}
🗃️ Paso 3: Configurar la Base de Datos AppDatabase
kotlin
Copiar
Editar
@Database(entities = [Tecnico::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tecnicoDao(): TecnicoDao

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
📦 Paso 4: Crear el Repository TecnicoRepository
kotlin
Copiar
Editar
class TecnicoRepository(private val tecnicoDao: TecnicoDao) {
    suspend fun login(email: String, password: String): Tecnico? {
        return tecnicoDao.login(email, password)
    }
}
🧠 Paso 5: Implementar el LoginViewModel
kotlin
Copiar
Editar
class LoginViewModel(private val repository: TecnicoRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    fun validarCredenciales(email: String, password: String) {
        viewModelScope.launch {
            val tecnico = repository.login(email, password)
            _loginResult.value = tecnico != null
        }
    }
}
🧩 Paso 6: Crear el SessionManager
kotlin
Copiar
Editar
class SessionManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun guardarSesion(id: Int, nombre: String) {
        prefs.edit().apply {
            putInt("user_id", id)
            putString("user_name", nombre)
            apply()
        }
    }

    fun obtenerSesion(): Pair<Int, String>? {
        val id = prefs.getInt("user_id", -1)
        val nombre = prefs.getString("user_name", null)
        return if (id != -1 && nombre != null) Pair(id, nombre) else null
    }

    fun eliminarSesion() {
        prefs.edit().clear().apply()
    }
}
📲 Paso 7: Implementar la LoginActivity
kotlin
Copiar
Editar
class LoginActivity : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tecnicoDao = AppDatabase.getDatabase(applicationContext).tecnicoDao()
        val repository = TecnicoRepository(tecnicoDao)
        val factory = LoginViewModelFactory(repository)
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        sessionManager = SessionManager(this)

        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginViewModel.validarCredenciales(email, password)
            } else {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        loginViewModel.loginResult.observe(this) { success ->
            if (success) {
                val tecnico = tecnicoDao.login(email, password)
                tecnico?.let {
                    sessionManager.guardarSesion(it.id, it.nombre)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
🧪 Paso 8: Implementar el LoginViewModelFactory
kotlin
Copiar
Editar
class LoginViewModelFactory(private val repository: TecnicoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
📝 Paso 9: Layout XML (activity_login.xml)
xml
Copiar
Editar
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <EditText
        android:id="@+id/edtEmail"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Correo electrónico"
        android:inputType="textEmailAddress" />

    <EditText
        android:id="@+id/edtPassword"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Contraseña"
        android:inputType="textPassword" />

    <Button
        android:id
::contentReference[oaicite:0]{index=0}



Tú dijiste:
Puedes explicarme este fragmento:

import java.security.MessageDigest

class TechServiceUtils {
    fun hashPassword(password: String): String{
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val hashBytes = messageDigest.digest(password.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}

ChatGPT Plus
Claro, te explico este fragmento línea por línea. Este código se encarga de encriptar (o más correctamente, hashear) contraseñas usando el algoritmo SHA-256.

📦 Código original:
kotlin
Copiar
Editar
import java.security.MessageDigest

class TechServiceUtils {
    fun hashPassword(password: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val hashBytes = messageDigest.digest(password.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}
🧠 ¿Qué hace este código?
✅ Propósito general:
Convierte una contraseña en un valor irreversible llamado hash. Esto se usa para almacenar contraseñas de forma segura. En lugar de guardar "123456", se guarda algo como "8d969eef6ecad3c29a3a629280e686cf...".

🔍 Explicación línea por línea
1. import java.security.MessageDigest
Importa la clase que permite usar funciones criptográficas de hash (como SHA-256, MD5, etc.).

2. class TechServiceUtils
Una clase utilitaria (puede contener más funciones reutilizables).

3. fun hashPassword(password: String): String
Función pública que recibe una contraseña (String) y devuelve su hash en formato hexadecimal (String).

4. val messageDigest = MessageDigest.getInstance("SHA-256")
Crea una instancia del algoritmo de hash SHA-256.

SHA-256 es seguro para uso general y recomendado para hashear contraseñas, aunque en producción es mejor usar bcrypt, scrypt o Argon2 porque son más lentos y protegen contra ataques de fuerza bruta.

5. val hashBytes = messageDigest.digest(password.toByteArray(Charsets.UTF_8))
Convierte la contraseña a bytes usando codificación UTF-8.

Luego, calcula el hash: el resultado es un arreglo de bytes (ByteArray), no una cadena legible.

6. return hashBytes.joinToString("") { "%02x".format(it) }
Convierte el ByteArray resultante en una cadena hexadecimal legible (ej: "a2b1c3...").

%02x convierte cada byte a 2 dígitos hexadecimales.

🧪 Ejemplo de uso
kotlin
Copiar
Editar
val hashed = TechServiceUtils().hashPassword("admin123")
println(hashed)
// Ejemplo de salida: e99a18c428cb38d5f260853678922e03abd8334b...
⚠️ Seguridad real
Aunque SHA-256 es criptográficamente seguro, para guardar contraseñas en producción es mejor usar:

🔐 bcrypt (muy recomendado)

🔐 PBKDF2

🔐 scrypt

🔐 Argon2

Estas alternativas agregan salt y retraso intencional para evitar ataques de fuerza bruta.

✅ En resumen
Parte	Explicación
MessageDigest.getInstance("SHA-256")	Obtiene el algoritmo SHA-256
.digest(...)	Calcula el hash de los bytes de la contraseña
.joinToString(...)	Convierte los bytes a texto hexadecimal
¿Para qué sirve?	Para almacenar contraseñas de forma segura (sin texto plano)

¿Te gustaría que te muestre cómo comparar una contraseña ingresada con una hasheada? ¿O cómo usar bcrypt en Android?



Tú dijiste:
Muéstrame cómo usar bcrypt en android por favor

ChatGPT Plus
¡Perfecto! Usar bcrypt en Android es una excelente forma de asegurar contraseñas porque está diseñado para ser lento (intencionalmente), lo que protege contra ataques de fuerza bruta.

✅ ¿Qué es bcrypt?
Es un algoritmo de hash seguro para contraseñas.

Agrega automáticamente un salt aleatorio.

Es lento a propósito, lo cual es deseable en temas de seguridad.

🛠️ Paso a paso: Cómo usar bcrypt en Android
🔹 Paso 1: Agrega la dependencia
Para usar bcrypt, puedes usar la siguiente biblioteca en tu archivo build.gradle:

gradle
Copiar
Editar
dependencies {
    implementation 'at.favre.lib:bcrypt:0.9.0'
}
Luego sincroniza el proyecto.

🔹 Paso 2: Clase utilitaria para hashear y verificar
kotlin
Copiar
Editar
import at.favre.lib.crypto.bcrypt.BCrypt

object PasswordUtils {

    fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }

    fun verifyPassword(password: String, hashedPassword: String): Boolean {
        val result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword)
        return result.verified
    }
}
🔍 Detalles:
12 es el "cost" o factor de trabajo (más alto = más seguro, pero más lento).

hashToString(...) genera automáticamente un salt único y devuelve el hash.

verify(...) compara una contraseña en texto plano con un hash generado.

🧪 Ejemplo de uso
kotlin
Copiar
Editar
val password = "admin123"

// Hashearla antes de guardarla
val hashed = PasswordUtils.hashPassword(password)
println("Hash: $hashed")

// Verificar cuando el usuario inicia sesión
val input = "admin123"
val isCorrect = PasswordUtils.verifyPassword(input, hashed)

if (isCorrect) {
    println("Contraseña válida")
} else {
    println("Contraseña incorrecta")
}
🧾 ¿Cómo se almacena?
El resultado de hashToString() es una cadena como esta:

perl
Copiar
Editar
$2a$12$XnxSWR2j7O7uQTV4SEti0eJkSRFYTSRO12ZCS0nJ.DuP8Qj2fJ3eW
Contiene:

Algoritmo ($2a)

Coste ($12)

Salt (XnxSWR2j...)

Hash

Todo eso se guarda como un único campo de tipo String en tu base de datos.

✅ Ventajas sobre SHA-256
Aspecto	SHA-256	bcrypt
Salt automático	❌ No	✅ Sí
Protección contra fuerza bruta	❌ No	✅ Sí (es lento intencionalmente)
Recomendado para contraseñas	❌ No (solo hashing rápido)	✅ Sí

¿Te gustaría que integre esto directamente en tu flujo de login y registro usando Room + bcrypt?



Tú dijiste:
Ok

ChatGPT Plus
¡Excelente! Vamos a integrar bcrypt en el flujo completo de registro e inicio de sesión usando Room + MVVM + bcrypt en Android.

🎯 Objetivo
🔐 Hashear contraseñas con bcrypt al registrarse.

🔍 Verificar contraseñas con bcrypt al iniciar sesión.

🗄️ Usar Room para almacenar los datos del usuario (técnico).

📦 Paso 1: Entidad Tecnico.kt
Asegúrate de que la contraseña se guarde como un String (el hash):

kotlin
Copiar
Editar
@Entity(tableName = "tecnico")
data class Tecnico(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val email: String,
    val password: String // Aquí se guardará el hash
)
🧪 Paso 2: PasswordUtils.kt
Clase para hashear y verificar contraseñas:

kotlin
Copiar
Editar
import at.favre.lib.crypto.bcrypt.BCrypt

object PasswordUtils {

    fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }

    fun verifyPassword(password: String, hashedPassword: String): Boolean {
        return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified
    }
}
🧩 Paso 3: TecnicoDao.kt
Agrega una función que busque el técnico solo por email (no por contraseña):

kotlin
Copiar
Editar
@Dao
interface TecnicoDao {
    @Insert
    suspend fun insert(tecnico: Tecnico)

    @Query("SELECT * FROM tecnico WHERE email = :email LIMIT 1")
    suspend fun getByEmail(email: String): Tecnico?
}
📚 Paso 4: TecnicoRepository.kt
kotlin
Copiar
Editar
class TecnicoRepository(private val tecnicoDao: TecnicoDao) {
    suspend fun registrar(tecnico: Tecnico) {
        tecnicoDao.insert(tecnico)
    }

    suspend fun buscarPorEmail(email: String): Tecnico? {
        return tecnicoDao.getByEmail(email)
    }
}
🧠 Paso 5: LoginViewModel.kt
kotlin
Copiar
Editar
class LoginViewModel(private val repository: TecnicoRepository) : ViewModel() {

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> get() = _loginSuccess

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val tecnico = repository.buscarPorEmail(email)
            if (tecnico != null && PasswordUtils.verifyPassword(password, tecnico.password)) {
                _loginSuccess.postValue(true)
            } else {
                _loginSuccess.postValue(false)
            }
        }
    }
}
🆕 Paso 6: RegistroViewModel.kt (registro nuevo técnico)
kotlin
Copiar
Editar
class RegistroViewModel(private val repository: TecnicoRepository) : ViewModel() {

    private val _registroExitoso = MutableLiveData<Boolean>()
    val registroExitoso: LiveData<Boolean> get() = _registroExitoso

    fun registrar(nombre: String, email: String, password: String) {
        viewModelScope.launch {
            val hashed = PasswordUtils.hashPassword(password)
            val tecnico = Tecnico(nombre = nombre, email = email, password = hashed)
            repository.registrar(tecnico)
            _registroExitoso.postValue(true)
        }
    }
}
👤 Paso 7: Actividad de Login
kotlin
Copiar
Editar
btnLogin.setOnClickListener {
    val email = edtEmail.text.toString().trim()
    val password = edtPassword.text.toString().trim()

    if (email.isNotEmpty() && password.isNotEmpty()) {
        loginViewModel.login(email, password)
    } else {
        Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
    }
}

loginViewModel.loginSuccess.observe(this) { success ->
    if (success) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    } else {
        Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
    }
}
📝 Paso 8: Registro de nuevo técnico
En tu RegistroActivity, al presionar "registrar":

kotlin
Copiar
Editar
registroViewModel.registrar(nombre, email, password)

registroViewModel.registroExitoso.observe(this) { exito ->
    if (exito) {
        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
        finish() // o redirige al login
    }
}
✅ Conclusión
Ya tienes un sistema completo de:

Registro de usuarios con contraseña hasheada (bcrypt)

Verificación segura de contraseñas en el login

Base de datos Room para almacenar técnicos

ViewModel + Repository para separar la lógica de UI

¿Quieres que prepare un repositorio base (GitHub) con este proyecto listo para compilar? ¿O que te ayude con el XML para la pantalla de registro?

 */