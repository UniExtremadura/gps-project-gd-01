package es.unex.giiis.marvelbook.ui.coleccion.tab.adapterTabs
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.unex.giiis.marvelbook.database.Personaje
import es.unex.giiis.marvelbook.databinding.ItemColeccionBinding

class PersonajeAdapter(
    private var personajes: List<Personaje>,
    private val onClick: (personaje: Personaje) -> Unit,
) : RecyclerView.Adapter<PersonajeAdapter.ShowViewHolder>() {

    class ShowViewHolder(
        private val binding: ItemColeccionBinding,
        private val onClick: (personaje: Personaje) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(personaje: Personaje) {
            with(binding) {
                nombrePersonajeLista.text = personaje.name

                Glide.with(fotoPersonaje.context)
                    .load(personaje.imagen.toString())
                    .into(fotoPersonaje)
                clItemLista.setOnClickListener {
                    onClick(personaje)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Personaje>) {
        personajes = newList
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

    override fun getItemCount() = personajes.size
    override fun onBindViewHolder(holder: ShowViewHolder, position:
    Int) {
        holder.bind(personajes[position])
    }
}
