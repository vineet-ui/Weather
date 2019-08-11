package com.example.weather.weather.controllers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.weather.weather.models.WeatherResponse;
import com.example.weather.weather.services.WeatherService;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WeatherController {

	public static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

	@Autowired
	WeatherService weatherService;

	@GetMapping(value = "/forecast")
	public ResponseEntity<Object> getWeatherByCity(@RequestParam("q") String city) {
		try {
			WeatherResponse weatherResponse = weatherService.getWeatherByCity(city);
			return new ResponseEntity(weatherResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, String> exceptions = new HashMap<>();
			exceptions.put("message", e.getMessage());
			return new ResponseEntity(exceptions, HttpStatus.BAD_REQUEST);
		}

	}

}
