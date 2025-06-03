package com.itesthida.techserviceapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
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
import com.itesthida.techserviceapp.data.database.repository.impl.EquipmentRepositoryImpl
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
        // Añadir otro cliente
        // Ver detalle del cliente
        // Ver todos los clientes
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

        // Inicializamos el repositorio para guardar el equipo del cliente
        equipmentRepository = EquipmentRepositoryImpl(this)

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

            // Mostramos AlertDialog al usuario
            showPostCreationDialog(customerId)
        }
    }

    private fun showPostCreationDialog(customerId: Long) {
        val options = arrayOf(
            getString(R.string.new_customer_option_detail_customer),
            getString(R.string.new_customer_option_create_another_customer),
            getString(R.string.new_customer_option_view_all_customers)
        )
        AlertDialog.Builder(this)
            .setTitle(R.string.new_customer_created_title)
            //.setMessage(R.string.new_customer_created_message) Cuando se hace uso de items, no se usa el message
            .setItems(options){ dialog, which ->
                when(which){
                    0 ->{// Detalle del cliente creado
                        val intent = Intent(this@AddCustomerActivity, CustomerDetailActivity::class.java)
                        intent.putExtra("customerId", customerId)
                        startActivity(intent)
                        finish()
                    }
                    1 ->{// Nuevo cliente
                        clearForm()
                        dialog.dismiss()
                    }
                    2 ->{// Ver todos los clientes
                            startActivity(Intent(this@AddCustomerActivity, CustomersListActivity::class.java))
                        finish()
                    }
                }
            }
            .setCancelable(false)
            .show()
    }

    private fun clearForm() {
        binding.etName.text.clear()
        binding.etLastName.text.clear()
        binding.etEmail.text.clear()
        binding.etPhone.text.clear()
        binding.eTAddress.text.clear()
        binding.etSerialNumber.text.clear()
        binding.spinnerEquipmentType.setSelection(0)
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
    }
}