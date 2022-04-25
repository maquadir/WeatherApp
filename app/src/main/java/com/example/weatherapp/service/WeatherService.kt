package com.example.weatherapp.service

import com.example.weatherapp.models.WeatherResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    suspend fun fetchWeatherInfoByQueryAsync(@Query("q") query: String, @Query("appid") appId: String = "335c74f83d33237c307031263d2d0475"): Response<WeatherResponse>

    companion object {
        val instance: WeatherService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(WeatherService::class.java)
        }
    }

}
