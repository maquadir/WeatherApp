package com.example.weatherapp.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.*
import com.example.weatherapp.models.WeatherResponse
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.service.WeatherService
import kotlinx.coroutines.*
import java.lang.Exception

class MainViewModel(private val weatherController: IWeatherInterface) : ViewModel() {

    private val currentWeatherLiveData = MutableLiveData<WeatherResponse>()
    private val progressMutableLiveData = MutableLiveData(false)
    val progressLiveData: LiveData<Boolean>
        get() = progressMutableLiveData

    val currentWeather: LiveData<WeatherResponse>
        get() = currentWeatherLiveData

    fun fetchCurrentWeather(input: String) {
        weatherController.getWeatherInfo(input) { isFetched, data ->
            if (isFetched) {
                currentWeatherLiveData.value = data
                progressMutableLiveData.value = false
            } else {
                currentWeatherLiveData.value = null
                progressMutableLiveData.value = false
            }
        }
    }
}