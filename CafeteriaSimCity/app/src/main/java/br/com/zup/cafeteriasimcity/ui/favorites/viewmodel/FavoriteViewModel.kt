package br.com.zup.cafeteriasimcity.ui.favorites.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.cafeteriasimcity.data.model.CoffeeResponse
import br.com.zup.cafeteriasimcity.domain.repository.FavoriteRepository
import br.com.zup.cafeteriasimcity.utils.FAVORITE_LIST_ERROR_MESSAGE
import br.com.zup.cafeteriasimcity.utils.FAVORITE_MESSAGE
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class FavoriteViewModel : ViewModel() {
    private val favoriteRepository = FavoriteRepository()

    private var _favoriteListState: MutableLiveData<List<String>> = MutableLiveData()
    val favoriteListState: LiveData<List<String>> = _favoriteListState

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun getListFavorite() {
        favoriteRepository.getListImageFavorited()
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    val favorites = mutableListOf<String>()

                    for (resultSnapshot in dataSnapshot.children) {
                        val result = resultSnapshot.getValue(String::class.java)
                        result?.let { favorites.add(it) }
                    }
                    _favoriteListState.value = favorites
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    _message.value = FAVORITE_LIST_ERROR_MESSAGE
                }
            })
    }

    fun removeImageFavorited(image: String) {
        val uri: Uri = Uri.parse(image)
        val path: String? = uri.lastPathSegment?.replace(".jpg", "")
        favoriteRepository.databaseReference().child("$path").removeValue()
    }
}