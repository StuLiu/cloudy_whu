package team.cloudy.dao;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import team.cloudy.pojo.Weather;

public interface WeatherDao {
	
	/*
	 * update weather in somewhere and someday
	 */
	public abstract void update(String rowkey, List<String> vals) throws IOException;
	
	public abstract Weather query(String addressId, Calendar date) throws IOException;
	
	/*
	 * ��ȡ�������������
	 * args:days ȡֵΪ������Ϊ�������ʾδ��|days|��,Ϊ������ʾ��ȥ|days|��,����������
	 */
	public abstract List<Weather> scan(String addressId, int days) throws IOException;
}
