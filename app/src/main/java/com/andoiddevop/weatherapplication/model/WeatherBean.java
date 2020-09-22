package com.andoiddevop.weatherapplication.model;

import java.util.List;

public class WeatherBean{
	private String countryCode;
	private String city_name;
	 List<DataItem> data;
	private String timezone;
	private double lon;
	private String stateCode;
	private double lat;

	public String getCountryCode(){
		return countryCode;
	}

	public String getCity_name(){
		return city_name;
	}

	public List<DataItem> getData(){
		return data;
	}

	public String getTimezone(){
		return timezone;
	}

	public double getLon(){
		return lon;
	}

	public String getStateCode(){
		return stateCode;
	}

	public double getLat(){
		return lat;
	}
}