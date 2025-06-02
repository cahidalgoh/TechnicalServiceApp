package com.itesthida.techserviceapp.ui.activities

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.itesthida.techserviceapp.R
import com.itesthida.techserviceapp.data.database.entities.Customer
import com.itesthida.techserviceapp.data.database.entities.Equipment
import com.itesthida.techserviceapp.data.database.entities.EquipmentType
import com.itesthida.techserviceapp.data.database.entities.Technician
import com.itesthida.techserviceapp.data.database.repository.CustomerRepository
import com.itesthida.techserviceapp.data.database.repository.EquipmentRepository
import com.itesthida.techserviceapp.data.database.repository.EquipmentTypeRepository
import com.itesthida.techserviceapp.data.database.repository.impl.CustomerRepositoryImpl
import com.itesthida.techserviceapp.data.database.repository.impl.EquipmentTypeRepositoryImpl
import com.itesthida.techserviceapp.databinding.ActivityAddCustomerBinding

class AddCustomerActivity : AppCompatActivity() {

    // Declaración de los objetos de la clase
    private lateinit var binding : ActivityAddCustomerBinding
    private lateinit var customerRepository: CustomerRepository
    private lateinit var equipmentTypeRepository: EquipmentTypeRepository
    private lateinit var equipmentRepository: EquipmentRepository
    private lateinit var equipmentTypes : List<EquipmentType>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_add_customer)

        // Si existe ténico, seguimos con la ejecución de la app.
        // Para el acceso a los componentes del layout
        // Inicializamos el binding pasándole la propiedad layoutInflater que ya está en el Activity
        binding = ActivityAddCustomerBinding.inflate(layoutInflater)

        // Para el acceso a los componentes del layout establecemos en la vista el activity desde el binding
        // inicializado con todas las referencias a los componentes que tengan un id en el layout
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Inicialización de los componentes
        initComponents()

        // Inicialización de los listeners
        initListeners()
    }

    /**
     * Inicializa los componentes declarados
     */
    private fun initComponents() {

        // Inicializamos el repositorio para gestionar los clientes
        customerRepository = CustomerRepositoryImpl(this)

        // Inicializamos el repositorio para obtener los tipos de equipos
        equipmentTypeRepository = EquipmentTypeRepositoryImpl(this)

        // Cargamos los tipos de equipos
        loadEquipmentTypes()

    }

    /**
     * Inicializa los listener de los componentes de la vista
     */
    private fun initListeners() {
        binding.btnSaveCustomer.setOnClickListener {
            // Obtenemos los datos ingresados por el usuario
            val name = binding.etName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val email = binding.etEmail.text.toString()
            val phone = binding.etPhone.text.toString()
            val address = binding.eTAddress.text.toString()

            // Guardamos el Cliente
            val customerId = saveCustomer(name, lastName, email, phone, address)!!

            val selectedType = equipmentTypes[binding.spinnerEquipmentType.selectedItemPosition]
            Log.d("Equipo selecionado: ${selectedType.id}", "${selectedType.toString()}")
            val serialNumber = binding.etSerialNumber.text.toString()

            // Guardamos el Equipo del cliente
            saveEquipment(customerId, selectedType.id, serialNumber)

        }
    }

    private fun loadEquipmentTypes(){
        // Cargar EquipmentTypes desde DB
        equipmentTypes = equipmentTypeRepository.getAll()

        Log.d("EQUIPMENT_TYPES", equipmentTypes.joinToString())

        val adapter = ArrayAdapter(this@AddCustomerActivity, android.R.layout.simple_spinner_item, equipmentTypes.map { "${it.name} (${it.brand} ${it.model})" })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerEquipmentType.adapter = adapter
    }

    private fun saveCustomer(name: String, lastName: String, email: String, phone: String, address: String): Long? {
        return customerRepository.insert(
            Customer(
                Customer.DEFAULT_ID,
                name,
                lastName,
                email,
                phone,
                address
            )
        )
    }

    private fun saveEquipment(customerId: Long, equipmentTypeId: Long, serialNumber: String) {
        val customer = customerRepository.getById(customerId)!!
        val equipmentType = equipmentTypeRepository.getById(equipmentTypeId)!!
        equipmentRepository.insert(
            Equipment(
                EquipmentType.DEFAULT_ID,
                customer,
                equipmentType,
                serialNumber
            )
        )

        /*

    val customer: Customer,
    val equipmentType: EquipmentType,
    val serialNumber: String
         */
    }
}