package com.example.weatherapp.viewmodel

import com.example.weatherapp.models.*
import com.example.weatherapp.repository.WeatherRepository

class WeatherControllerTest(private val weatherRepository: WeatherRepository) : IWeatherInterface {

    var isWeatherFetched = false
    val weatherResponse = WeatherResponse(
        "", Clouds(0), 0, Coord(0.0, 0.0),
        0, 0, Main(0, 0, 0.0, 0.0, 0.0), "", Sys("", 0, 0.0, 0, 0, 0),
        0, emptyList(), Wind(0, 0.0)
    )

    override fun getWeatherInfo(input: String, callback: (Boolean, WeatherResponse?) -> Unit) {
        if (input.isNotEmpty() && isWeatherFetched) {
            callback(true, weatherResponse)
        } else {
            callback(false, null)
        }
    }

}