package com.itesthida.techserviceapp.ui.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.itesthida.techserviceapp.R
import com.itesthida.techserviceapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Declaración de los objetos de la clase
    private lateinit var binding :ActivityMainBinding

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
    }

    /**
     * Inicializa los componentes declarados
     */
    private fun initComponents() {
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
}
