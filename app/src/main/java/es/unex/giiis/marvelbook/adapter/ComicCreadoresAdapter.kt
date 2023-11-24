package es.unex.giiis.marvelbook.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.unex.giiis.marvelbook.database.Creador
import es.unex.giiis.marvelbook.databinding.DetallesItemBinding

class ComicCreadoresAdapter(private val creadores: List<Creador>) :
    RecyclerView.Adapter<ComicCreadoresAdapter.ShowViewHolder>() {

    class ShowViewHolder(private val binding: DetallesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(creador1: Creador, creador2: Creador?) {
            with(binding) {

                nombreItemLista.text = creador1.name

                Glide.with(fotoItem.context)
                    .load(creador1.imagen.toString())
                    .into(fotoItem)


                if (creador2 != null) {

                    nombreItemLista2.text = creador2.name

                    Glide.with(fotoItem2.context)
                        .load(creador2.imagen.toString())
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

    override fun getItemCount() = (creadores.size + 1) / 2

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val index1 = position * 2
        val index2 = if (index1 + 1 < creadores.size) index1 + 1 else null
        holder.bind(creadores[index1], index2?.let { creadores[it] })
    }
}