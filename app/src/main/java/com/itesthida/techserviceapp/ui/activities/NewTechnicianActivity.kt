package com.itesthida.techserviceapp.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.itesthida.techserviceapp.R
import com.itesthida.techserviceapp.data.database.entities.Technician
import com.itesthida.techserviceapp.data.database.repository.TechnicianRepository
import com.itesthida.techserviceapp.data.database.repository.impl.TechnicianRepositoryImpl
import com.itesthida.techserviceapp.databinding.ActivityNewTechnicianBinding
import com.itesthida.techserviceapp.utils.SessionManager
import com.itesthida.techserviceapp.utils.TechServiceUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewTechnicianActivity : AppCompatActivity() {

    // Declaración de los objetos de la clase
    private lateinit var session: SessionManager
    private lateinit var binding: ActivityNewTechnicianBinding
    private lateinit var technicianRepository: TechnicianRepository
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_new_technician)

        // Para el acceso a los componentes del layout
        // Inicializamos el binding a través de la clase que representa el layout del login
        // pasándole la propiedad layoutInflater que ya está en el Activity
        binding = ActivityNewTechnicianBinding.inflate(layoutInflater)

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

        // Inicialización de los listeners
        initListeners()
    }

    /**
     * Inicialización de variables declaradas
     */
    private fun initComponents() {

        // Inicializamos el SessionManager
        session = SessionManager(this)

        // Inicializamos el repositorio
        technicianRepository = TechnicianRepositoryImpl(this)

        // Obtenemos las sharedPreferences
        sharedPreferences = getSharedPreferences("TechServicePrefs", Context.MODE_PRIVATE)
    }

    /**
     * Inicializa los listeners
     */
    private fun initListeners() {
        // Listener para el botón btnRegisterTechnician
        binding.btnRegisterTechnician.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val lastName = binding.etLastName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val repeatPassword = binding.etRepeatPassword.text.toString().trim()

            if(name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()){
                // No se han informado todos los campos, mostramos mensaje al usuario
                Toast.makeText(this, getString(R.string.login_incomplete_fields), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != repeatPassword) {
                Toast.makeText(this, getString(R.string.new_technician_different_password), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!TechServiceUtils.isValidEmail(email)) {
                Toast.makeText(this, getString(R.string.new_technician_invalid_email), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                val technician = technicianRepository.getByEmail(email)
                if(technician != null){
                    runOnUiThread {
                        Toast.makeText(this@NewTechnicianActivity, getString(R.string.new_technician_exists_email), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Guardamos el técnico
                    val hashedPassword = TechServiceUtils.hashPassword(password)
                    val technician = Technician(
                        id = 0,
                        name = name,
                        lastName = lastName,
                        email = email,
                        password = hashedPassword
                    )
                    technicianRepository.insert(technician)
                    runOnUiThread {
                        Toast.makeText(this@NewTechnicianActivity, getString(R.string.new_technician_registration_ok), Toast.LENGTH_SHORT).show()
                        // Redirigir a la pantalla principal
                        val intent = Intent(this@NewTechnicianActivity, LoginActivity::class.java)
                        // Borra toda la pila de actividades actual
                        // Inicia una nueva actividad (LoginActivity) como la única en la nueva pila
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                }
            }
        }

    }
}
/*


<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".ui.activities.NewTechnicianActivity"
    android:paddingHorizontal="48dp">

    <androidx.appcompat.widget.LinearLayoutCompat
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/new_technician_text"
            android:textSize="30sp"
            android:textStyle="italic|bold"
            android:layout_gravity="center"/>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/new_technician_name"
            android:textSize="22sp"
            android:textStyle="italic|bold"/>

        <EditText
            android:id="@+id/etName"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="@string/new_technician_write_name"
            android:inputType="text"
            android:layout_marginBottom="20dp"/>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/new_technician_last_name"
            android:textSize="22sp"
            android:textStyle="italic|bold"/>

        <EditText
            android:id="@+id/etLastName"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="@string/new_technician_write_last_name"
            android:inputType="text"
            android:layout_marginBottom="20dp"/>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/new_technician_email"
            android:textSize="22sp"
            android:textStyle="italic|bold"/>

        <EditText
            android:id="@+id/etEmail"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="@string/new_technician_write_email"
            android:inputType="text"
            android:layout_marginBottom="20dp"/>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/new_technician_password"
            android:textSize="22sp"
            android:inputType="textEmailAddress"
            android:textStyle="italic|bold"/>

        <EditText
            android:id="@+id/etPassword"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="@string/new_technician_write_password"
            android:inputType="textPassword"
            android:layout_marginBottom="20dp"/>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/new_technician_repeat_password"
            android:textSize="22sp"
            android:textStyle="italic|bold"/>

        <EditText
            android:id="@+id/etRepeatPassword"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="@string/new_technician_repeat_password_hint"
            android:inputType="textPassword"
            android:layout_marginBottom="32dp"/>

        <Button
            android:id="@+id/btnRegisterTechnician"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/new_technician_btn_register"
            android:layout_gravity="center"/>

    </androidx.appcompat.widget.LinearLayoutCompat>

</androidx.constraintlayout.widget.ConstraintLayout>



 */