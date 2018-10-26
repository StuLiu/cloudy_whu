package team.cloudy.service;

import java.util.Date;

import team.cloudy.pojo.Weather;

public interface WeatherService {
	
	/**
	 * @author liuwang  
	 * @date 2018年10月26日 下午4:49:26  
	 * @version 1.0   
	 * @TODO 查询某地某时的天气状况
	 * @param [地点，时间]
	 * @return 天气对象
	 */
	public abstract Weather getWeather(String address, Date time);
}
