package team.cloudy.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import team.cloudy.dao.WeatherDao;
import team.cloudy.dao.impl.WeatherDaoImpl;
import team.cloudy.pojo.Weather;

public class WeatherUpdater {
	
	private static final String url_prefix = "http://t.weather.sojson.com/api/weather/city/";
	
	private static List<Weather> getWeather(String city_id) throws Exception  {
		List<Weather> result = new ArrayList<Weather>();
		String url_str = url_prefix + city_id;
		// 根据链接（字符串格式），生成一个URL对象
		URL url = new URL(url_str);	
		// 打开URL
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));// 得到输入流，即获得了网页的内容
        String line; // 读取输入流的数据，并显示
        String web_str = "";
        while ((line = reader.readLine()) != null) {
        	web_str += line;
        }
        System.out.println(web_str);
        
        //匹配（最高温度）（最低温度）（风向）（风速）（天气类型）
//        String pattern = "forecast.*?\"high\": \"高温(.*?)℃.*?low\":\"低温(.*?)℃.*?fx\":\"(.*?)\".*?fl\":\"(.*?)\".*?type\":\"(.*?)\"";
        String pattern = "\"high\":\"高温(.*?)℃.*?低温(.*?)℃.*?fx\":\"(.*?)\".*?fl\":\"(.*?)\".*?type\":\"(.*?)\"";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(web_str);
        
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, -1);
        while(m.find()) {
        	String addressId_time = city_id + "_" + date.get(Calendar.YEAR) + "_" + 
    				(date.get(Calendar.MONTH) + 1)+ "_" + date.get(Calendar.DAY_OF_MONTH);
        	System.out.println("address_time: " + addressId_time );
			System.out.println("高温: " + m.group(1) );
			System.out.println("低温: " + m.group(2) );
			System.out.println("风向: " + m.group(3) ); 
			System.out.println("风速: " + m.group(4) );
			System.out.println("天气类型: " + m.group(5) );
			Weather weather = new Weather(addressId_time, m.group(5), m.group(3), m.group(4),
					m.group(2), m.group(1),  "");
			result.add(weather);
			date.add(Calendar.DATE, 1);
        }
        return result;
	}
	
	public static void doUpdate() throws Exception{
		WeatherDao wd = new WeatherDaoImpl();
		List<String> cityIds = CityPaser.getCityIds();
		for(String cityId : cityIds){
			List<Weather> weather_list = getWeather(cityId);
			for(Weather weather : weather_list){
				wd.update(weather);
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		WeatherUpdater.doUpdate();
//		WeatherUpdater.getWeather("101250201");
	}
}
