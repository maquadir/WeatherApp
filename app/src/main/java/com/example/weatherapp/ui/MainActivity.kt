package com.example.weatherapp.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.service.WeatherService
import com.example.weatherapp.viewmodel.MainViewModel
import com.example.weatherapp.viewmodel.MainViewModelFactory
import com.example.weatherapp.viewmodel.WeatherController
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity(), LocationListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2

    private val weatherService = WeatherService.instance
    private val weatherRepository = WeatherRepository(weatherService)

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(WeatherController(weatherRepository))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialiseObservers()
        initialiseUIElements()
    }

    private fun initialiseUIElements() {
        val user = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        user.apply {
            binding.name.text = getString("name", "name")
            binding.description.text = getString("description", "description")
            binding.humidity.text = getString("humidity", "humidity")
            binding.pressure.text = getString("Pressure", "pressure")
            binding.temp.text = getString("Temperature", "temperature")
        }

        binding.getWeatherButton.setOnClickListener {
            binding.loadingProgressBar.visibility = View.VISIBLE
            viewModel.fetchCurrentWeather(binding.searchEditText.text.toString())
        }

        binding.getCurrentLocation.setOnClickListener {
            getLocation()
        }
    }

    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
        }
    }

    override fun onLocationChanged(location: Location) {
        val gpsLocation = getCityFromCoordinates(location)
        binding.loadingProgressBar.visibility = View.VISIBLE
        viewModel.fetchCurrentWeather(gpsLocation)
    }

    private fun getCityFromCoordinates(loc: Location): String {
        var cityName: String? = null
        val gcd = Geocoder(baseContext, Locale.getDefault())
        val addresses: List<Address>
        try {
            addresses = gcd.getFromLocation(
                loc.latitude,
                loc.longitude, 1
            )
            if (addresses.isNotEmpty()) {
                cityName = addresses[0].locality
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return cityName ?: ""
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initialiseObservers() {
        viewModel.currentWeather.observe(this) { response ->
            binding.name.text = getString(R.string.location, response.name)
            binding.description.text = response.weather[0].description
            binding.humidity.text = getString(R.string.humidity, response.main.humidity.toString())
            binding.pressure.text = getString(R.string.pressure, response.main.pressure.toString())
            binding.temp.text =
                getString(R.string.temp, ((response.main.temp - 273.15).toInt()).toString())
            binding.tempMax.text =
                getString(R.string.max_temp, ((response.main.temp_max - 273.15).toInt()).toString())
            binding.tempMin.text =
                getString(R.string.min_temp, ((response.main.temp_min - 273.15).toInt()).toString())
        }

        viewModel.progressLiveData.observe(this) {
            binding.loadingProgressBar.isVisible = it
        }
    }

    override fun onPause() {
        super.onPause()
        saveDataLocally()
    }

    override fun onDestroy() {
        super.onDestroy()
        saveDataLocally()
    }

    private fun saveDataLocally() {
        val user = getSharedPreferences("PREFERENCE_NAME", MODE_PRIVATE)
        val editor = user.edit()
        editor.apply {
            putString("name", binding.name.text.toString())
            putString("description", binding.description.text.toString())
            putString("humidity", binding.humidity.text.toString())
            putString("Pressure", binding.pressure.text.toString())
            putString("Temperature", binding.temp.text.toString())
            commit()
        }
    }
}