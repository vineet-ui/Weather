package com.example.weather.weather.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherResponse {

	@SerializedName("cod")
	@Expose
	private String cod;
	@SerializedName("message")
	@Expose
	private Double message;
	@SerializedName("cnt")
	@Expose
	private Integer cnt;
	@SerializedName("list")
	@Expose
	private java.util.List<com.example.weather.weather.models.List> list = null;
	@SerializedName("city")
	@Expose
	private City city;

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public Double getMessage() {
		return message;
	}

	public void setMessage(Double message) {
		this.message = message;
	}

	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	public java.util.List<com.example.weather.weather.models.List> getList() {
		return list;
	}

	public void setList(java.util.List<com.example.weather.weather.models.List> list) {
		this.list = list;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

}
