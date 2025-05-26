package com.itesthida.techserviceapp.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.itesthida.techserviceapp.R

class NewTechnicianActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_technician)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
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