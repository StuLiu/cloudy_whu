package team.cloudy.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.cloudy.dao.WeatherDao;
import team.cloudy.dao.impl.WeatherDaoImpl;
import team.cloudy.pojo.Weather;
import team.cloudy.service.WeatherService;

@Service
public class WeatherServiceImpl implements WeatherService {
	
	@Autowired
	private WeatherDao weatherDao;
	
//	public WeatherServiceImpl(){ weatherDao = new WeatherDaoImpl(); }
	
	@Override
	public List<Weather> getWeather(String addressId, int days) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("/WeatherService.getWeather");
		return weatherDao.scan(addressId, days);
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		WeatherServiceImpl ws = new WeatherServiceImpl();
		System.out.println(ws.getWeather("54398", -100));
	}

}
