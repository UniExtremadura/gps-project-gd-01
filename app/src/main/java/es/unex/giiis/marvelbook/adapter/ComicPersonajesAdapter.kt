package es.unex.giiis.marvelbook.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.unex.giiis.marvelbook.database.Personaje
import es.unex.giiis.marvelbook.databinding.DetallesItemBinding

class ComicPersonajesAdapter(private val personajes: List<Personaje>) :
    RecyclerView.Adapter<ComicPersonajesAdapter.ShowViewHolder>() {

    class ShowViewHolder(private val binding: DetallesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(personaje1: Personaje, personaje2: Personaje?) {
            with(binding) {

                nombreItemLista.text = personaje1.name

                Glide.with(fotoItem.context)
                    .load(personaje1.imagen.toString())
                    .into(fotoItem)

                // Check if there is a second comic
                if (personaje2 != null) {

                    nombreItemLista2.text = personaje2.name

                    Glide.with(fotoItem2.context)
                        .load(personaje2.imagen.toString())
                        .into(fotoItem2)
                } else {

                    cardView2.visibility = android.view.View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val binding = DetallesItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ShowViewHolder(binding)
    }

    override fun getItemCount() = (personajes.size + 1) / 2

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val index1 = position * 2
        val index2 = if (index1 + 1 < personajes.size) index1 + 1 else null
        holder.bind(personajes[index1], index2?.let { personajes[it] })
    }
}