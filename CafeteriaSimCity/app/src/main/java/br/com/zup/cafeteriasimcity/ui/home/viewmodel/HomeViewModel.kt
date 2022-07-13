package br.com.zup.cafeteriasimcity.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zup.cafeteriasimcity.data.datasource.remote.RetrofitService
import br.com.zup.cafeteriasimcity.data.model.CoffeeResponse
import br.com.zup.cafeteriasimcity.domain.repository.AuthRepository
import br.com.zup.cafeteriasimcity.utils.FAVORITE_ERROR_MESSAGE
import br.com.zup.cafeteriasimcity.utils.FAVORITE_MESSAGE
import br.com.zup.cafeteriasimcity.utils.IMAGE_COFFEE_ERROR_MESSAGE
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _coffeeResponse = MutableLiveData<CoffeeResponse>()
    val coffeeResponse: LiveData<CoffeeResponse> = _coffeeResponse

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

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
                _message.value = IMAGE_COFFEE_ERROR_MESSAGE
            } finally {
                _loading.value = false
            }
        }
    }

    fun saveImageFavorited() {
        try {
            authRepository.databaseReference()
                ?.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val databaseReference = authRepository.databaseReference()
                        databaseReference?.setValue(_coffeeResponse.value?.arquivo.toString())
                        _message.value = FAVORITE_MESSAGE
                    }

                    override fun onCancelled(error: DatabaseError) {
                        _message.value = error.message
                    }
                })
            _message.value = FAVORITE_MESSAGE
        } catch (ex: Exception) {
            _message.value = FAVORITE_ERROR_MESSAGE
        }
    }

    fun getNameUser() = authRepository.getNameUser()

    fun getEmailUser() = authRepository.getEmailUser()

    fun logoutUser() = authRepository.logoutUser()
}