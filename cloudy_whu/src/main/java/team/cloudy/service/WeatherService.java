package team.cloudy.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import team.cloudy.pojo.Weather;

@Service
public interface WeatherService {
	
	/**
	 * @author liuwang  
	 * @date 2018年10月26日 下午4:49:26  
	 * @version 1.0   
	 * @TODO 查询某地未来五天天气状况
	 * @param 
	 * @return 天气对象的数组
	 * @throws IOException 
	 */
	public abstract List<Weather> getNextFiveDaysWeather(String addressId) throws IOException;
	
	/**
	 * @author liuwang  
	 * @date 2018年10月26日 下午4:49:26  
	 * @version 1.0   
	 * @TODO 查询某地过去五天天气状况
	 * @param 
	 * @return 天气对象的数组
	 * @throws IOException 
	 */
	public abstract List<Weather> getPrevFiveDaysWeather(String addressId) throws IOException;
}
