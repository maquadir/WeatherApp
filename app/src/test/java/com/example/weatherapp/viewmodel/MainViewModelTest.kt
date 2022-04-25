package com.example.weatherapp.viewmodel

import android.util.Log
import com.example.weatherapp.models.WeatherResponse
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.service.WeatherService
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel

    lateinit var weatherRepository: WeatherRepository

    lateinit var weatherService : WeatherService

    lateinit var weatherController: WeatherControllerTest


    init {
        setup()
    }

    private fun setup() {
        weatherService = WeatherService.instance
        weatherRepository = WeatherRepository(weatherService)
        weatherController = WeatherControllerTest(weatherRepository)
        mainViewModel = MainViewModel(weatherController)
    }

    @Test
    fun test_fetch_weather_info() {
        weatherController.isWeatherFetched = true
        mainViewModel.fetchCurrentWeather("Sydney")

        mainViewModel.currentWeather.observeForever {
            Log.i("Tag", it.toString())
        }

        assertEquals(mainViewModel.currentWeather.value, "WeatherResponse()") // Passes
    }

}