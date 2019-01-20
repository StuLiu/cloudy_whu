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
	 * 获取连续几天的天气
	 * args:days 取值为整数。为正数则表示未来|days|天,为负数表示过去|days|天,均包括当天
	 */
	public abstract List<Weather> scan(String addressId, int days) throws IOException;
}
