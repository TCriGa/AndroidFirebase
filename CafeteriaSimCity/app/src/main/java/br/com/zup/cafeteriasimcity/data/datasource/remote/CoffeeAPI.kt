package br.com.zup.cafeteriasimcity.data.datasource.remote

import br.com.zup.cafeteriasimcity.data.model.CoffeeResponse
import retrofit2.http.GET

interface CoffeeAPI {
    @GET("random.json")
    suspend fun getImageCoffee(): CoffeeResponse
}