package br.com.zup.cafeteriasimcity.ui.home.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import br.com.zup.cafeteriasimcity.R
import br.com.zup.cafeteriasimcity.data.model.CoffeeResponse
import br.com.zup.cafeteriasimcity.databinding.ActivityHomeBinding
import br.com.zup.cafeteriasimcity.ui.home.viewmodel.HomeViewModel
import br.com.zup.cafeteriasimcity.ui.login.view.LoginActivity
import com.squareup.picasso.Picasso

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getImageCoffee()
        intObserver()
    }

    private fun intObserver() {
        viewModel.coffeeResponse.observe(this) {
            loadImage(it)
        }

        viewModel.errorMessage.observe(this) {
            loadErrorMessage(it)
        }

        viewModel.loading.observe(this) {
            binding.pbLoading.isVisible = it == true
        }
    }

    private fun loadImage(coffeeResponse: CoffeeResponse) {
        Picasso.get().load(coffeeResponse.arquivo)
            .into(binding.ivCoffeeDay)
    }

    private fun loadErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exit -> {
                startActivity(Intent(this, LoginActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}