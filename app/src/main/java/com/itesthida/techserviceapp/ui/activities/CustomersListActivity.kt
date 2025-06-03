package com.itesthida.techserviceapp.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.itesthida.techserviceapp.R
import com.itesthida.techserviceapp.data.database.entities.Customer
import com.itesthida.techserviceapp.data.database.repository.CustomerRepository
import com.itesthida.techserviceapp.data.database.repository.impl.CustomerRepositoryImpl
import com.itesthida.techserviceapp.databinding.ActivityCustomersListBinding
import com.itesthida.techserviceapp.ui.activities.adapters.CustomerAdapter

class CustomersListActivity : AppCompatActivity() {
    // Declaramos el binding
    lateinit var binding: ActivityCustomersListBinding

    // Adapter
    lateinit var adapter: CustomerAdapter

    lateinit var customerRepository: CustomerRepository

    var customerList : List<Customer> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_customers_list)

        // Inicialización de los componentes
        initComponents()

        initUI()
    }

    private fun initComponents() {
        customerRepository = CustomerRepositoryImpl(this)

        // Construimos el binding
        binding = ActivityCustomersListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main){ v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initUI() {

        // Obtenemos los clientes desde la base de datos
        customerList = customerRepository.getAll()

        // Creamos el adapter con la lista
        adapter = CustomerAdapter(customerList)

        // Configuramos el RecyclerView
        binding.rvCustomers.adapter = adapter

        binding.rvCustomers.layoutManager = LinearLayoutManager(this)
    }
}