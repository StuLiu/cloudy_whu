package team.cloudy.service;

import java.io.IOException;
import java.util.List;

import team.cloudy.pojo.Weather;

public interface WeatherService {
	
	/**
	 * @author liuwang  
	 * @date 2018年10月26日 下午4:49:26  
	 * @version 1.0   
	 * @TODO 查询某地某时的天气状况
	 * @param 
	 * @return 天气对象的数组
	 * @throws IOException 
	 */
	public abstract List<Weather> getWeather(String addressId, int days) throws IOException;
}
