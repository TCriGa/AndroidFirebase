package br.com.zup.cafeteriasimcity.ui.favorite.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.zup.cafeteriasimcity.databinding.ImageCoffeeItemBinding
import com.squareup.picasso.Picasso

class FavoriteAdapter(
    private var favoriteList: MutableList<String>,
    private val onCLick: (image: String) -> Unit
) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ImageCoffeeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favoriteImage = favoriteList[position]
        holder.showImage(favoriteImage)
        holder.binding.ivRemoveFavorite.setOnClickListener {
            onCLick(favoriteImage)
        }
    }

    override fun getItemCount() = favoriteList.size

    fun updateMovieList(newList: MutableList<String>) {
        favoriteList = newList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ImageCoffeeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun showImage(imageCoffeeResponse: String) {
            Picasso.get().load(imageCoffeeResponse)
                .into(binding.ivCoffeeDayItem)
        }
    }
}