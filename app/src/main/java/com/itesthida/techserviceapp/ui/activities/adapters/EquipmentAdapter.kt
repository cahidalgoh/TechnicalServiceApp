package com.itesthida.techserviceapp.ui.activities.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.itesthida.techserviceapp.data.database.entities.Equipment
import com.itesthida.techserviceapp.databinding.ItemEquipmentBinding

class EquipmentAdapter(
    var items: List<Equipment>
) : Adapter<EquipmentViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentViewHolder {
        // Para el acceso a los componentes del layout
        // Ahora en lugar de inflar la vista a partir del xml, se lo pedimos al binding
        val binding = ItemEquipmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        // Para el acceso a los componentes del layout
        // Devolvemos el vieHolder ahora pasando el binding
        return EquipmentViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: EquipmentViewHolder, position: Int) {
        // Esta función se ejecuta cada vez que se va a visualizar una celda
        // Obtenemos el equipment que se va a pintar en la posición que nos pasan como parámetro
        val equipment = items[position]

        // Al holder le indicamos el equipment a pintar con los datos
        holder.render(equipment)

    }

    fun updateList(newItems: List<Equipment>) {
        items = newItems
        notifyDataSetChanged()
    }


}


// Para el acceso a los componentes del layout
// Cambiamos el objeto que recibe en el constructor (Un View por un ItemTaskBinding) y también actualizamos el origen del view en el padre
// cambiamos el view por la vista que representa toda la celda, ente caso es un LinearLayout
// Para poder acceder al binding y las propiedades de la vista dentro de la clase, hay declarar como una propiedad en el constructor,
// le ponemos val delante del nombre del objeto en el constructor
class EquipmentViewHolder(val binding: ItemEquipmentBinding) : ViewHolder(binding.root){

    // Recibe el equipment, del cual se van a obtener los datos que se van a pintar en la vista
    fun render(equipment: Equipment){
        // Para el acceso a los componentes del layout
        // Accedemos a los componentes de la vista a través del binding
        // Pintamos el tipo de equipo y el número de serie
        binding.tvType.text = equipment.equipmentType.name
        binding.tvSerial.text = equipment.serialNumber
    }
}