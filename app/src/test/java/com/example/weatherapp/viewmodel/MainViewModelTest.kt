package com.example.weatherapp.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.util.Log
import androidx.lifecycle.Observer
import com.example.weatherapp.models.WeatherResponse
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.service.WeatherService
import com.nhaarman.mockito_kotlin.mock
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mainViewModel: MainViewModel

    private lateinit var factory: MainViewModelFactory

    lateinit var weatherRepository: WeatherRepository

    lateinit var weatherService: WeatherService

    lateinit var weatherController: WeatherControllerTest

    lateinit var weatherObserver: Observer<WeatherResponse>
    lateinit var loadingObserver: Observer<Boolean>

    @Before
    fun setup() {
        weatherRepository = mock()
        factory = mock()
        mainViewModel = MainViewModel(weatherController)

        weatherObserver = mock()
        loadingObserver = mock()

        mainViewModel.currentWeather.observeForever(weatherObserver)
        mainViewModel.progressLiveData.observeForever(loadingObserver)
    }


    @Test
    fun test_fetch_weather_info() {
        weatherController.isWeatherFetched = true
        mainViewModel.fetchCurrentWeather("Sydney")


        assertEquals(mainViewModel.currentWeather.value, "WeatherResponse()") // Passes
    }

}