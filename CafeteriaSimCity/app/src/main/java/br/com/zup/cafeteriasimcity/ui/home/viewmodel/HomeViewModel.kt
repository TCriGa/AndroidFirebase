package br.com.zup.cafeteriasimcity.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zup.cafeteriasimcity.data.datasource.remote.RetrofitService
import br.com.zup.cafeteriasimcity.data.model.CoffeeResponse
import br.com.zup.cafeteriasimcity.domain.repository.AuthRepository
import br.com.zup.cafeteriasimcity.utils.IMAGE_COFFEE_ERROR_MESSAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _coffeeResponse = MutableLiveData<CoffeeResponse>()
    val coffeeResponse: LiveData<CoffeeResponse> = _coffeeResponse

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getImageCoffee() {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitService.apiService.getImageCoffee()
                }
                _coffeeResponse.value = response
            } catch (ex: Exception) {
                _errorMessage.value = IMAGE_COFFEE_ERROR_MESSAGE
            } finally {
                _loading.value = false
            }
        }
    }

    fun getNameUser() = authRepository.getNameUser()

    fun getEmailUser() = authRepository.getEmailUser()

    fun logoutUser() = authRepository.logoutUser()
}