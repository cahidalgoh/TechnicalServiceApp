package com.itesthida.techserviceapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.itesthida.techserviceapp.R
import com.itesthida.techserviceapp.databinding.ActivityMainBinding
import com.itesthida.techserviceapp.utils.SessionManager

class MainActivity : AppCompatActivity() {

    // Declaración de los objetos de la clase
    private lateinit var session: SessionManager
    private lateinit var binding: ActivityMainBinding

    private lateinit var technicianName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_main)

        // Si existe ténico, seguimos con la ejecución de la app.
        // Para el acceso a los componentes del layout
        // Inicializamos el binding pasándole la propiedad layoutInflater que ya está en el Activity
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Para el acceso a los componentes del layout establecemos en la vista el activity desde el binding
        // inicializado con todas las referencias a los componentes que tengan un id en el layout
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicialización de las variables y/o componentes
        initComponents()

        // Para el acceso a las pantallas comprobamos si hay técnico en la session
        if(isThereAnActiveSession()){

            // Inicialización de los listeners
            initListeners()

            // inicialización de los datos en la intefaz gráfica
            initUI()

        }
    }

    /**
     * Inicializa los componentes declarados
     */
    private fun initComponents() {
        // Inicializamos la variable session
        session = SessionManager(this)
    }

    /**
     * Inicializa los listener de los componentes de la vista
     */
    private fun initListeners() {
        binding.btnCustomers.setOnClickListener {
            // Para la pantalla Add Customer
            val intent = Intent(this, AddCustomerActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Go to Customers View", Toast.LENGTH_SHORT).show()
        }

        binding.btnOrders.setOnClickListener {
            Toast.makeText(this, "Go to Orders View", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Inicializa los componentes de la interfaz gráfica con los datos obtenidos previamente
     */
    private fun initUI() {
        // Obtenemos el nombre del técnico de  la session
        technicianName = session.getTechnicianName()!!
        binding.tvWelcome.text = String.format(getString(R.string.tech_service_welcome_text), technicianName)
    }

    /**
     * Menu de la pantalla principal
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /**
     * Comportamiento de los items del menu
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.actionConfig -> {
                Toast.makeText(this, "Open Config", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.actionAbout -> {
                Toast.makeText(this, "Open About", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.actionLogout -> {
                //Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()

                // Cerramos la cesión del usuario
                session.logout()

                // Redireccionamos a la pantalla de login
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                // Para que no vuelva a esta pantalla si hace uso del botón volver
                finish()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Comprueba si existe una sesión activa
     */
    private fun isThereAnActiveSession(): Boolean {
        // Comprobamos el id del técnico
        if(!session.isLoggedIn()){
            // No hay session guardada
            // Redirigimos a la pantalla de inicio de session
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

            // Para que no vuelva a esta pantalla si hace uso del botón volver
            finish()

            return false
        }

        // Existe session
        val nombreTecnico = session.getTechnicianName()
        Toast.makeText(this, getString(R.string.tech_service_message_login_ok), Toast.LENGTH_SHORT).show()

        return true

    }
}
