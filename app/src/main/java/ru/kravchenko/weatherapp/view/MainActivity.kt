package ru.kravchenko.weatherapp.view

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import ru.kravchenko.weatherapp.R
import ru.kravchenko.weatherapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var GET: SharedPreferences
    private lateinit var SET:SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        var cName = GET.getString("cityName","London")
        edt_city_name.setText(cName)

        viewModel.refreshData(cName!!)

        getLiveData()

        swipe_refresh_layout.setOnRefreshListener {
            ll_data_view.visibility =View.GONE
            tv_error.visibility = View.GONE
            pb_loading.visibility = View.GONE

            var cityName = GET.getString("cityName", cName)
            edt_city_name.setText(cityName)
            viewModel.refreshData(cityName!!)
            swipe_refresh_layout.isRefreshing = false
        }

        img_search_city.setOnClickListener {
            val cityName = edt_city_name.text.toString()
            SET.putString("cityName", cityName)
            SET.apply()
            viewModel.refreshData(cityName)
            getLiveData()
        }

    }

    @SuppressLint("CheckResult", "SetTextI18n")
    private fun getLiveData() {
        viewModel.weatherData.observe(this, Observer { data ->
            data?.let {
                pb_loading.visibility = View.GONE
                ll_data_view.visibility = View.VISIBLE
                tv_degree.text = data.main.temp.toInt().toString() + "°C"
                tv_country_code.text = data.sys.country.toString()
                tv_city_name.text = data.name.toString()
                tv_humidity.text = ": " + data.main.humidity.toString() + "%"
                tv_wind_speed.text = ": " + data.wind.speed.toString() + " km/h"
                tv_lat.text = ": " + data.coord.lat.toString()
                tv_lon.text = ": " + data.coord.lon.toString()

                Glide.with(this).load("http://openweathermap.org/img/wn/" + data.weather[0].icon + "@2x.png")
                    .into(img_weather_icon)
            }
        })

        viewModel.weatherLoad.observe(this, Observer { load ->
            load.let {
                if (it) {
                    pb_loading.visibility = View.VISIBLE
                    tv_error.visibility = View.GONE
                    ll_data_view.visibility = View.GONE
                }else{
                    pb_loading.visibility = View.GONE
                }
            }
        })
        viewModel.weatherError.observe(this, Observer { error ->
            error.let {
                if (it) {
                    tv_error.visibility = View.VISIBLE
                    ll_data_view.visibility = View.GONE
                    pb_loading.visibility = View.GONE
                }else{
                    tv_error.visibility = View.GONE
                }
            }
        })
    }
}

