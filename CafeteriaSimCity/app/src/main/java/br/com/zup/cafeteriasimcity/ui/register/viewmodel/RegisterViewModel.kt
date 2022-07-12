package br.com.zup.cafeteriasimcity.ui.register.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.cafeteriasimcity.domain.model.User
import br.com.zup.cafeteriasimcity.domain.repository.AuthRepository
import br.com.zup.cafeteriasimcity.utils.CREATE_USER_ERROR_MESSAGE
import br.com.zup.cafeteriasimcity.utils.EMAIL_ERROR_MESSAGE
import br.com.zup.cafeteriasimcity.utils.NAME_ERROR_MESSAGE
import br.com.zup.cafeteriasimcity.utils.PASSWORD_ERROR_MESSAGE

class RegisterViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private var _registerState = MutableLiveData<User>()
    val registerState: LiveData<User> = _registerState

    private var _errorState = MutableLiveData<String>()
    val errorState: LiveData<String> = _errorState

    private fun registerUser(user: User) {
        try {
            authRepository.registerUser(user.email, user.password).addOnSuccessListener { task ->
                authRepository.updateUserName(user.name)?.addOnSuccessListener {
                    _registerState.value = user
                }
            }.addOnFailureListener {
                _errorState.value = CREATE_USER_ERROR_MESSAGE
            }
        } catch (ex: Exception) {
            _errorState.value = ex.message
        }
    }

    fun validateDate(user: User) {
        when {
            user.name.isEmpty() -> {
                _errorState.value = NAME_ERROR_MESSAGE
            }
            user.email.isEmpty() -> {
                _errorState.value = EMAIL_ERROR_MESSAGE
            }
            user.password.isEmpty() -> {
                _errorState.value = PASSWORD_ERROR_MESSAGE
            }
            else -> {
                registerUser(user)
            }
        }
    }
}