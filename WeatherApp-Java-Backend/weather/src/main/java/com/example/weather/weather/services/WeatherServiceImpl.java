package com.example.weather.weather.services;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.weather.weather.connectors.Connector;
import com.example.weather.weather.connectors.RESTConnector;
import com.example.weather.weather.constants.Constants;
import com.example.weather.weather.controllers.WeatherController;
import com.example.weather.weather.models.WeatherResponse;
import com.google.gson.Gson;

@Service("WeatherService")
public class WeatherServiceImpl implements WeatherService {

	public static final Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public WeatherResponse getWeatherByCity(String city) throws Exception {
		Connector connector = applicationContext.getBean(RESTConnector.class);
		connector.initialize(Constants.WEATHER_API_HOST, Constants.WEATHER_API_BY_CITY_URL);
		Map<String, String> httpParameterMap = new HashMap<>();
		httpParameterMap.put("q", city);
		httpParameterMap.put("appId", Constants.WEATHER_API_KEY);
		connector.setHttpParameterMap(httpParameterMap);
		connector.execute();
		HttpResponse httpResponse = connector.getResponse();
		Integer httpResponseStatusCode = httpResponse.getStatusLine().getStatusCode();
		if (httpResponseStatusCode >= HttpStatus.OK.value()
				&& httpResponseStatusCode < HttpStatus.MULTIPLE_CHOICES.value()) {
			String responseStr = connector.getStringResponse();
			logger.info("********************** RESPONSE ***************************");
			logger.info(responseStr);
			logger.info("********************** RESPONSE ***************************");
			return (new Gson()).fromJson(responseStr, WeatherResponse.class);
		} else if(httpResponseStatusCode == HttpStatus.NOT_FOUND.value()){
			throw new Exception("Given city not found");
		} else {
			throw new Exception("Something went wrong.");
		}
	}

}
