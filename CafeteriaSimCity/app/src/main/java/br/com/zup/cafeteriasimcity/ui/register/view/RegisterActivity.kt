package br.com.zup.cafeteriasimcity.ui.register.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import br.com.zup.cafeteriasimcity.databinding.ActivityRegisterBinding
import br.com.zup.cafeteriasimcity.domain.model.User
import br.com.zup.cafeteriasimcity.ui.home.view.HomeActivity
import br.com.zup.cafeteriasimcity.ui.register.viewmodel.RegisterViewModel
import br.com.zup.cafeteriasimcity.utils.USER_KEY
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btRegister.setOnClickListener {
            val user = getDataUser()
            viewModel.validateDataUser(user)
        }

        initObservers()
    }

    private fun getDataUser(): User {
        return User(
            name = binding.etNameRegister.text.toString(),
            email = binding.etEmailRegister.text.toString(),
            password = binding.etPasswordRegister.text.toString()
        )
    }

    private fun initObservers() {
        viewModel.registerState.observe(this) {
            goToHome(it)
        }

        viewModel.errorState.observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun goToHome(user: User) {
        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra(USER_KEY, user)
        }
        startActivity(intent)
    }
}