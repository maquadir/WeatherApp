package com.example.weatherapp.repository

import androidx.lifecycle.LiveData
import com.example.weatherapp.models.WeatherResponse
import com.example.weatherapp.service.WeatherService

class WeatherRepository(private val weatherService: WeatherService) {

    suspend fun fetchWeatherInfoByQuery(queryText: String) =
        weatherService.fetchWeatherInfoByQueryAsync(queryText)

}
