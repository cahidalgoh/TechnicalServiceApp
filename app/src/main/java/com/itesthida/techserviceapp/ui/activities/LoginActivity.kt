package com.itesthida.techserviceapp.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.itesthida.techserviceapp.R
import com.itesthida.techserviceapp.databinding.ActivityLoginBinding
import com.itesthida.techserviceapp.utils.SessionManager

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

        // Inicializamos el SessionManager
        session = SessionManager(this)
    }

    /**
     * Inicializa los listeners
     */
    private fun initListeners() {
        binding.btnLogin.setOnClickListener {
            // Obtenemos los datos introducidos por el usuario
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()){
                // Comprobamos las credenciales
                validateCredentials(username, password)
            } else{
                // Mensaje al usuario

            }
        }
    }

    /**
     * Valida las credenciales introducidas por el usuario
     */
    private fun validateCredentials(username: String, password: String) {
        TODO("Not yet implemented")
    }
}