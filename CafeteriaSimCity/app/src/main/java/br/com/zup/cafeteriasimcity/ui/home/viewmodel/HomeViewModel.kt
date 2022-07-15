package br.com.zup.cafeteriasimcity.ui.home.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zup.cafeteriasimcity.data.datasource.remote.RetrofitService
import br.com.zup.cafeteriasimcity.data.model.CoffeeResponse
import br.com.zup.cafeteriasimcity.domain.repository.AuthenticationRepository
import br.com.zup.cafeteriasimcity.domain.repository.FavoriteRepository
import br.com.zup.cafeteriasimcity.utils.FAVORITE_MESSAGE
import br.com.zup.cafeteriasimcity.utils.FAVORITE_PATH
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    private val authenticationRepository = AuthenticationRepository()
    private val favoriteRepository = FavoriteRepository()

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
                _message.value = "Tivemos algum problema, tente novamente!"
            } finally {
                _loading.value = false
            }
        }
    }

    fun saveImageFavorited() {
        val image = _coffeeResponse.value?.arquivo.toString()
        val imagePath = getImagePath()

        favoriteRepository.databaseReference().child("$imagePath")
            .setValue(image) { error, reference ->
                if (error != null) {
                    _message.value = error.message
                }
                _message.value = FAVORITE_MESSAGE
            }
    }

    private fun getImagePath(): String? {
        val image = _coffeeResponse.value?.arquivo.toString()
        val uri: Uri = Uri.parse(image)
        return uri.lastPathSegment?.replace(".jpg", "")
    }

    fun getUserName() = authenticationRepository.getNameUser()

    fun getUserEmail() = authenticationRepository.getEmailUser()

    fun logout() = authenticationRepository.logoutOut()
}