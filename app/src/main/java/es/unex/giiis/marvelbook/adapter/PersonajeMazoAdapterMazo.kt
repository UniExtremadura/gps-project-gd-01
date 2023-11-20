package es.unex.giiis.marvelbook.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.unex.giiis.marvelbook.R
import es.unex.giiis.marvelbook.database.PersonajeMazo
import es.unex.giiis.marvelbook.databinding.ItemMazoBinding

class PersonajeMazoAdapterMazo(
    private var personajes: List<PersonajeMazo>,
    private val onFavClickListener: (position: Int) -> Unit
) : RecyclerView.Adapter<PersonajeMazoAdapterMazo.ShowViewHolder>() {

    fun updateList(newList: List<PersonajeMazo>) {
        personajes = newList
        notifyDataSetChanged()
    }

    class ShowViewHolder(
        private val binding: ItemMazoBinding,
        private val onFavClickListener: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.logofav.setOnClickListener {
                onFavClickListener(adapterPosition)
            }
        }

        fun bind(personajeMazo: PersonajeMazo) {
            with(binding) {
                namePersonajeMazo.text = personajeMazo.name
                speedPersonajeMazo.text = personajeMazo.speed.toString()
                defendPersonajeMazo.text = personajeMazo.defense.toString()
                powerPersonajeMazo.text = personajeMazo.power.toString()

                if(personajeMazo.fav == true){
                    logofav.setImageResource(R.drawable.baseline_favorite_24)
                }
                else{
                    logofav.setImageResource(R.drawable.baseline_favorite_border_24)
                }
                Glide.with(fotoPersonajeSobre.context)
                    .load(personajeMazo.imagen.toString())
                    .into(fotoPersonajeSobre)

            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val binding = ItemMazoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ShowViewHolder(binding, onFavClickListener)
    }
    override fun getItemCount() = personajes.size
    override fun onBindViewHolder(holder: ShowViewHolder, position:
    Int) {
        holder.bind(personajes[position])
    }
}
