package com.example.weatherapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.models.WeatherResponse
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.ui.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

interface IWeatherInterface {
    fun getWeatherInfo(input: String, callback: (Boolean, WeatherResponse?) -> Unit)
}

class WeatherController(
    private val weatherRepository: WeatherRepository
) : IWeatherInterface,
    ViewModel() {

    override fun getWeatherInfo(input: String, callback: (Boolean, WeatherResponse?) -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val weatherInfo =
                    withContext(Dispatchers.IO) { weatherRepository.fetchWeatherInfoByQuery(input) }
                callback(true, weatherInfo.body())
            } catch (e: Exception) {
                callback(false, null)
            }
        }
    }
}