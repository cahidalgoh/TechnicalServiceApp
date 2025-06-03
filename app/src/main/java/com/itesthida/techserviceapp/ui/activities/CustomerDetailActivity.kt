package com.itesthida.techserviceapp.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.itesthida.techserviceapp.R

class CustomerDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_customer_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recuperamos el id del cliente
        val customerId = intent.getLongExtra("customerId", -1)

        if (customerId != -1L) {
            // Aquí puedes luego cargar el detalle real del cliente
            Toast.makeText(this, "${getString(R.string.new_customer_created_message)}. ID: $customerId", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, getString(R.string.detail_customer_error_get_data), Toast.LENGTH_SHORT).show()
        }

    }
}