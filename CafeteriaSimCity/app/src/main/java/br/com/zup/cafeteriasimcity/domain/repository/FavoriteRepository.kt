package br.com.zup.cafeteriasimcity.domain.repository

import br.com.zup.cafeteriasimcity.utils.FAVORITE_PATH
import br.com.zup.cafeteriasimcity.utils.IMAGE_COFFEE_PATH
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.ktx.Firebase

class FavoriteRepository {


    private val authentication: FirebaseAuth = Firebase.auth
    private val database = FirebaseDatabase.getInstance()
    private val reference =
        database.getReference("$IMAGE_COFFEE_PATH/${authentication.currentUser?.uid}/$FAVORITE_PATH")

    fun databaseReference() = reference

    fun getListImagesFavorited(): Query {

        return reference.orderByValue()
    }

}