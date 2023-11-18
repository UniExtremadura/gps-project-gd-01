package es.unex.giiis.marvelbook.ui.coleccion.tab.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.unex.giiis.marvelbook.database.Comic
import es.unex.giiis.marvelbook.databinding.ItemColeccionBinding

class ComicAdapter (

    private val comics: List<Comic>,
    private val onClick: (comics: Comic) -> Unit,
) : RecyclerView.Adapter<ComicAdapter.ShowViewHolder>() {

    class ShowViewHolder(
        private val binding: ItemColeccionBinding,
        private val onClick: (comic: Comic) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comic: Comic, totalItems: Int) {
            with(binding) {
                nombrePersonajeLista.text = comic.title

                Glide.with(fotoPersonaje.context)
                    .load(comic.imagen.toString())
                    .into(fotoPersonaje)
                clItemLista.setOnClickListener {
                    onClick(comic)
                }

            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType:
    Int): ShowViewHolder {
        val binding =

            ItemColeccionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        return ShowViewHolder(binding, onClick)
    }
    override fun getItemCount() = comics.size
    override fun onBindViewHolder(holder: ShowViewHolder, position:
    Int) {
        holder.bind(comics[position], comics.size)
    }
}