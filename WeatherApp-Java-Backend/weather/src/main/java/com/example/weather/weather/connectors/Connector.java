package com.example.weather.weather.connectors;

import java.util.Map;

import org.apache.http.HttpResponse;

public interface Connector {
	public void initialize(String host, String path);

	public void setHttpHeaderMap(Map<String, String> httpHeaderMap);

	public void setHttpParameterMap(Map<String, String> httpParameterMap);

	public void execute();

	public HttpResponse getResponse();

	public String getStringResponse();
}
