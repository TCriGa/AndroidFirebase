package br.com.zup.cafeteriasimcity.data.model

import com.google.gson.annotations.SerializedName

data class CoffeeResponse(
    @SerializedName("file")
    val arquivo: String = ""
)