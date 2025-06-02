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
import com.itesthida.techserviceapp.data.database.repository.impl.TechnicianRepositoryImpl
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
        //setContentView(R.layout.activity_login)

        //Para el acceso a los componentes del layout
        // Inicializamos el binding a través de la clase que representa el layout del login
        // pasándole la propiedad layoutInflater que ya está en el Activity
        binding = ActivityLoginBinding.inflate(layoutInflater)

        // Para el acceso a los componentes del layout establecemos en la vista el activity desde el binding
        // inicializado con todas las referencias a los componentes que tengan un id en el layout
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        // Inicialización de las variables y/o componentes
        initComponents()

        // Inicialización de los listeners
        initListeners()
    }

    /**
     * Inicialización de variables declaradas
     */
    private fun initComponents() {

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
        val technicianRepository = TechnicianRepositoryImpl(this)

        CoroutineScope(Dispatchers.IO).launch {
            val technician = technicianRepository.login(email, password)

            runOnUiThread {
                if(technician != null){
                    // Guardamos la session
                    session.saveSession(technician.id, technician.name)

                    Toast.makeText(this@LoginActivity, getString(R.string.login_success), Toast.LENGTH_SHORT).show()

                    // Redirigimos a la pantalla principal
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Datos de login incorrectos
                    // Mensaje al usuario
                    Toast.makeText(this@LoginActivity, getString(R.string.login_error_message), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
// https://m2.material.io/components/text-fields/android#using-text-fields