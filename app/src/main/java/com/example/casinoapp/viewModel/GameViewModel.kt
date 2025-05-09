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
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface RemoteGameInterface {

    @GET("/user/{id}/fondocoins")
    suspend fun getUserFondoCoins(@Path("id") id: Int): Response<Int>

    @PUT("/user/{id}/fondocoins")
    suspend fun updateUserFondoCoins(
        @Path("id") id: Int,
        @Body newFondoCoins: Int
    ): Response<Map<String, String>>

    @GET("/user/{id}/experience")
    suspend fun getUserExperience(@Path("id") id: Int): Response<Int>

    @PUT("/user/{id}/experience")
    suspend fun updateUserExperience(
        @Path("id") id: Int,
        @Body newExperience: Int
    ): Response<Map<String, String>>
}

class GameViewModel : ViewModel() {
    private val _fondocoins = MutableStateFlow(0)
    val fondocoins: StateFlow<Int> get() = _fondocoins
    private val _experience = MutableStateFlow(0)
    val experience: StateFlow<Int> get() = _experience

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val endpoint: RemoteGameInterface = retrofit.create(RemoteGameInterface::class.java)

    fun placeBet(betAmount: Int): Boolean {
        return if (_fondocoins.value >= betAmount) {
            _fondocoins.value -= betAmount
            true
        } else {
            false
        }
    }

    fun getUserFondoCoins(userId: Int) {
        viewModelScope.launch {
            try {
                val response = endpoint.getUserFondoCoins(userId)
                if (response.isSuccessful) {
                    _fondocoins.value = response.body() ?: 0
                } else {
                    Log.e("GameViewModel", "Error en la respuesta: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("GameViewModel", "Error obteniendo fondocoins: ${e.message}", e)
            }
        }
    }

    fun updateUserFondoCoins(userId: Int, newFondoCoins: Int) {
        viewModelScope.launch {
            try {
                val response = endpoint.updateUserFondoCoins(userId, newFondoCoins)
                if (response.isSuccessful) {
                    _fondocoins.value = newFondoCoins
                } else {
                    Log.e("GameViewModel", "Error actualizando fondocoins: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("GameViewModel", "Error actualizando fondocoins: ${e.message}", e)
            }
        }
    }

    fun getUserExperience(userId: Int) {
        viewModelScope.launch {
            try {
                val response = endpoint.getUserExperience(userId)
                if (response.isSuccessful) {
                    _experience.value = response.body() ?: 0
                } else {
                    Log.e("GameViewModel", "Error en la respuesta: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("GameViewModel", "Error obteniendo experiencia: ${e.message}", e)
            }
        }
    }

    fun updateUserExperience(userId: Int, newExperience: Int) {
        viewModelScope.launch {
            try {
                val response = endpoint.updateUserExperience(userId, newExperience)
                if (response.isSuccessful) {
                    _experience.value = newExperience
                } else {
                    Log.e("GameViewModel", "Error actualizando experiencia: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("GameViewModel", "Error actualizando experiencia: ${e.message}", e)
            }
        }
    }
}
