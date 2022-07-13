package br.com.zup.cafeteriasimcity.domain.repository

import br.com.zup.cafeteriasimcity.utils.IMAGE_COFFEE_PATH
import br.com.zup.cafeteriasimcity.utils.IMAGE_PATH
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class AuthRepository {
    private val auth: FirebaseAuth = Firebase.auth
    private val database = FirebaseDatabase.getInstance()
    private val reference = database.getReference(IMAGE_COFFEE_PATH)

    fun loginUser(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun logoutUser() {
        auth.signOut()
    }

    fun registerUser(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun updateUserName(name: String): Task<Void>? {
        val profile = UserProfileChangeRequest.Builder()
            .setDisplayName(name).build()
        return auth.currentUser?.updateProfile(profile)
    }

    fun getCurrentUser() = auth.currentUser

    fun getNameUser(): String? = auth.currentUser?.displayName

    fun getEmailUser(): String? = auth.currentUser?.email

    fun databaseReference(): DatabaseReference? {
        val key = reference.push().key
        return key?.let { reference.child(it) }
    }

    fun getListImageFavorited(): Query {
        return reference.orderByKey()
    }
}