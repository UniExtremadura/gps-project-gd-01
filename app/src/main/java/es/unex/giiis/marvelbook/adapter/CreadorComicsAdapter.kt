package es.unex.giiis.marvelbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.unex.giiis.marvelbook.database.Comic
import es.unex.giiis.marvelbook.databinding.DetallesItemBinding

class CreadorComicsAdapter(private val comics: List<Comic>) :
    RecyclerView.Adapter<CreadorComicsAdapter.ShowViewHolder>() {

    class ShowViewHolder(private val binding: DetallesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comic1: Comic, comic2: Comic?) {
            with(binding) {

                nombreItemLista.text = comic1.title

                Glide.with(fotoItem.context)
                    .load(comic1.imagen.toString())
                    .into(fotoItem)


                if (comic2 != null) {

                    nombreItemLista2.text = comic2.title

                    Glide.with(fotoItem2.context)
                        .load(comic2.imagen.toString())
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

    override fun getItemCount() = (comics.size + 1) / 2

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val index1 = position * 2
        val index2 = if (index1 + 1 < comics.size) index1 + 1 else null
        holder.bind(comics[index1], index2?.let { comics[it] })
    }
}