package br.com.zup.cafeteriasimcity.ui.login.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.zup.cafeteriasimcity.databinding.ActivityLoginBinding
import br.com.zup.cafeteriasimcity.domain.model.User
import br.com.zup.cafeteriasimcity.ui.home.view.HomeActivity
import br.com.zup.cafeteriasimcity.ui.register.view.RegisterActivity
import br.com.zup.cafeteriasimcity.utils.USER_KEY

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvRegisterNow?.setOnClickListener {
            goToRegister()
        }

        binding.bvLogin?.setOnClickListener {
            val user = getDataUser()
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

    private fun getDataUser(): User {
        return User(
            email = binding.etUserEmail?.text.toString(),
            password = binding.etPassword?.text.toString()
        )
    }

    private fun goToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun goToHome(user: User) {
        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra(USER_KEY, user)
        }
        startActivity(intent)
    }
}