package com.example.casinoapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PUT

interface RemoteGameInterface {

    @GET("/user/{id}/fondocoins")
    suspend fun getUserFondoCoins(@retrofit2.http.Path("id") id: Int): Response<Int>

    @FormUrlEncoded
    @PUT("/user/{id}/fondocoins")
    suspend fun updateUserFondoCoins(
        @retrofit2.http.Path("id") id: Int,
        @Field("fondocoins") fondocoins: Int
    ): Response<Map<String, String>>

}

class GameViewModel : ViewModel() {
    private val _fondocoins = MutableStateFlow(0)
    val fondocoins: StateFlow<Int> = _fondocoins

    fun getUserFondoCoins(userId: Int) {
        viewModelScope.launch {
            try {
                val connection = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val endpoint = connection.create(RemoteGameInterface::class.java)
                val response = endpoint.getUserFondoCoins(userId)

                if (response.isSuccessful) {
                    _fondocoins.value = response.body() ?: 0
                }
            } catch (e: Exception) {
                Log.e("GameViewModel", "Error obteniendo fondocoins: ${e.message}")
            }
        }
    }

    fun updateUserFondoCoins(userId: Int, newFondoCoins: Int) {
        viewModelScope.launch {
            try {
                val connection = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val endpoint = connection.create(RemoteGameInterface::class.java)
                val response = endpoint.updateUserFondoCoins(userId, newFondoCoins)

                if (response.isSuccessful) {
                    _fondocoins.value = newFondoCoins
                }
            } catch (e: Exception) {
                Log.e("GameViewModel", "Error actualizando fondocoins: ${e.message}")
            }
        }
    }
}