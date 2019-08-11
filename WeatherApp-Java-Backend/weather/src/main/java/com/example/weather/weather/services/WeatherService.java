package com.example.weather.weather.services;

import com.example.weather.weather.models.WeatherResponse;

public interface WeatherService {
	public WeatherResponse getWeatherByCity(String city) throws Exception;
}
