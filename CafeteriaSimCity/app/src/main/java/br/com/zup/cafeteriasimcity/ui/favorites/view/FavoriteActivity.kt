package br.com.zup.cafeteriasimcity.ui.favorites.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.zup.cafeteriasimcity.databinding.ActivityFavoriteBinding
import br.com.zup.cafeteriasimcity.ui.favorites.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    private val viewModel: FavoriteViewModel by lazy {
        ViewModelProvider(this)[FavoriteViewModel::class.java]
    }

    private val adapter: FavoriteAdapter by lazy {
        FavoriteAdapter(arrayListOf(), ::removeFavoriteImage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getListFavorite()
        setUpRecyclerView()
        initObservers()
    }

    private fun setUpRecyclerView() {
        binding.rvImageCoffee.layoutManager = LinearLayoutManager(this)
        binding.rvImageCoffee.adapter = adapter
    }

    private fun initObservers() {
        viewModel.favoriteListState.observe(this) {
            adapter.updateMovieList(it.toMutableList())
        }

        viewModel.message.observe(this) {
            loadMessage(it)
        }
    }

    private fun removeFavoriteImage(image: String){
        viewModel.removeImageFavorited(image)
    }

    private fun loadMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}