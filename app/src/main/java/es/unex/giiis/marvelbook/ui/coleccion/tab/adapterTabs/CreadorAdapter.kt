package es.unex.giiis.marvelbook.ui.coleccion.tab.adapterTabs

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.unex.giiis.marvelbook.database.Creador
import es.unex.giiis.marvelbook.databinding.ItemColeccionBinding

class CreadorAdapter (

    private var creadores: List<Creador>,
    private val onClick: (creadores: Creador) -> Unit,
    ) : RecyclerView.Adapter<CreadorAdapter.ShowViewHolder>() {

        class ShowViewHolder(
            private val binding: ItemColeccionBinding,
            private val onClick: (creador: Creador) -> Unit,
        ) : RecyclerView.ViewHolder(binding.root) {
            fun bind(creador: Creador) {
                with(binding) {
                    nombrePersonajeLista.text = creador.name

                    Glide.with(fotoPersonaje.context)
                        .load(creador.imagen.toString())
                        .into(fotoPersonaje)
                    clItemLista.setOnClickListener {
                        onClick(creador)
                    }

                }
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        fun updateList(newList: List<Creador>) {
            creadores = newList
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType:
        Int): ShowViewHolder {
            val binding =

                ItemColeccionBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false)
            return ShowViewHolder(binding, onClick)
        }
        override fun getItemCount() = creadores.size
        override fun onBindViewHolder(holder: ShowViewHolder, position:
        Int) {
            holder.bind(creadores[position])
        }
}
