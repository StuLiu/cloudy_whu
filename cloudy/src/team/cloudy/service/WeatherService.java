package team.cloudy.service;

import java.io.IOException;
import java.util.List;

import team.cloudy.pojo.Weather;

public interface WeatherService {
	
	/**
	 * @author liuwang  
	 * @date 2018��10��26�� ����4:49:26  
	 * @version 1.0   
	 * @TODO ��ѯĳ��ĳʱ������״��
	 * @param 
	 * @return �������������
	 * @throws IOException 
	 */
	public abstract List<Weather> getWeather(String addressId, int days) throws IOException;
}
