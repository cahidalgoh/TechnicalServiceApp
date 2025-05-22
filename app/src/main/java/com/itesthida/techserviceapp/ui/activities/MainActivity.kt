package com.itesthida.techserviceapp.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
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
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Inicialización de las variables y/o componentes
        initComponents()

        // Inicialización de los listeners
        initListeners()

        // inicialización de los datos en la intefaz gráfica
        initUI()
    }

    /**
     * Inicializa los componentes declarados
     */
    private fun initComponents() {
        // Inicializamos la variable session
        session = SessionManager(this)
        // Obtenemos el nombre del técnico
        /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
         *
         * - * Pendiente de obtenenr el nombre de la sesión * - *
         *
         */
        technicianName = "Marley"
        //Para el acceso a los componentes del layout
        // Inicializamos el binding pasándole la propiedad layoutInflater que ya está en el Activity
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Para el acceso a los componentes del layout establecemos en la vista el activity desde el binding
        // inicializado con todas las referencias a los componentes que tengan un id en el layout
        setContentView(binding.root)
    }

    /**
     * Inicializa los listener de los componentes de la vista
     */
    private fun initListeners() {
        binding.btnCustomers.setOnClickListener {
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
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
