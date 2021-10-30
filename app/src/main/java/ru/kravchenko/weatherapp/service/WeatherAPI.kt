package ru.kravchenko.weatherapp.service

import io.reactivex.Single
import retrofit2.http.GET
import ru.kravchenko.weatherapp.model.WeatherModel

interface WeatherAPI {

    @GET("data/2.5/weather?q=London&units=metric&appid=6a2c78e775ad28e76cd3ca2d9cc8643d")
    fun getData(): Single<WeatherModel>

}

//https://api.openweathermap.org/data/2.5/weather?q=London&units=metric&appid=6a2c78e775ad28e76cd3ca2d9cc8643d