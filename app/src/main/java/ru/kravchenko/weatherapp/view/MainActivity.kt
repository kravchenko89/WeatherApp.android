package ru.kravchenko.weatherapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.kravchenko.weatherapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

//https://api.openweathermap.org/data/2.5/weather?q=London&units=metric&appid=6a2c78e775ad28e76cd3ca2d9cc8643d