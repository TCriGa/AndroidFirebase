package br.com.zup.cafeteriasimcity.ui.favorite.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.zup.cafeteriasimcity.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    private val adapter: FavoriteAdapter by lazy {
        FavoriteAdapter(arrayListOf(), ::removeFavoriteImage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.rvImageCoffee.layoutManager = LinearLayoutManager(this)
        binding.rvImageCoffee.adapter = adapter
    }
    
    private fun removeFavoriteImage(image: String) {
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}