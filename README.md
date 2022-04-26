# WeatherApp
A simple android mobile application that allows users to get location based weather information from OpenWeatherMap Api. The app allows a user to input a location or use a gps, for current location, to get weather updates.

# Application Design & Architecture
The native Android application is written in Kotlin using Android studio.

The app follows the MVVM architecture with the following concepts utilized in the application

- View binding
- View Model
- Repository Pattern for abstraction of data fecthing
- Retorfit for HTTP calls to the API
- Coroutines for async processing
- Mockito for Unit tests(minimal)

<img width="339" alt="image" src="https://user-images.githubusercontent.com/19331629/165207641-30053307-2f68-4e11-bf5a-952bd959876c.png">

# Following are the Program Packages
- models - consists of all data entities
- repository - responsible for abstraction of data fetching
- service - responsible for calls to api
- ui - responsible for displaying data
- viewmodel - responsible holding application state/data

# Explicit Requirements
- Use the OpenWeatherMap API: http://openweathermap.org/api. You may use any of json, xml, or another payload.
- You may create your own API key or use this one: 95d190a434083879a6398aafd54d9e73
- Please do not use the map solution provided by openweathermap.org
- Unit test your code(minimal)

# High-level requirements
- Search by city name or post code - Implement a search to allow the user to enter a city name or post code. The result of the search is to
display the current weather information for the searched location.
- Search by GPS - On the same search screen, also allow user to use GPS location instead to get current weather information.
- Most recent search location loads automatically - When you come back to the app after closing it, the weather for the most recent search is displayed.
