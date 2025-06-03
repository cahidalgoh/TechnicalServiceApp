package com.itesthida.techserviceapp.ui.activities.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.itesthida.techserviceapp.data.database.entities.Customer
import com.itesthida.techserviceapp.databinding.ItemCustomerBinding

class CustomerAdapter(
    var items: List<Customer>
) : Adapter<CustomerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val binding = ItemCustomerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomerViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val customer = items[position]

        holder.render(customer)
    }

    fun updateItems(items: List<Customer>){
        this.items = items
        notifyDataSetChanged()
    }
}

class CustomerViewHolder(val binding: ItemCustomerBinding) : ViewHolder(binding.root){

    private val equipmentAdapter = EquipmentAdapter(emptyList())

    init {
        binding.rvEquipments.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = equipmentAdapter
            setHasFixedSize(true)
            isNestedScrollingEnabled = false // Mejor usar la propiedad Kotlin directamente
        }
    }

    fun render(customer: Customer){
        binding.tvCustomerName.text = customer.name
        binding.tvCustomerEmail.text = customer.email

        // Solo actualiza la lista, no vuelvas a asignar adapter ni layoutManager
        equipmentAdapter.updateList(customer.equipments)
    }


    /*
    private val equipmentAdapter = EquipmentAdapter(emptyList()) // ahora es un ListAdapter

    init {
        binding.rvEquipments.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = equipmentAdapter
            setHasFixedSize(true)
        }
    }
    // Recibe el Customer del cual se van a obtener los datos que se van a pintar en la vista
    fun render(customer: Customer){
        binding.tvCustomerName.text = customer.name
        binding.tvCustomerEmail.text = customer.email

        // RecyclerView de equipos
        equipmentAdapter.updateList(customer.equipments)
    }
    */
}