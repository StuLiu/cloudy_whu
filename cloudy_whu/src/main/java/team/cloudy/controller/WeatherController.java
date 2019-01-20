package team.cloudy.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;
import team.cloudy.service.WeatherService;
import team.cloudy.utils.CityPaser;

@Controller
@RequestMapping("/weather")
public class WeatherController {
	
	@Autowired
	private WeatherService weatherService;
	
	@ResponseBody
	@RequestMapping(value="/getFuture")
	public JSONObject getFuture(HttpServletRequest request, @RequestBody Map<String,String> map) {
		JSONObject result= new JSONObject();
		try{
			String addressName = map.get("city");
			System.out.println("/getFuture:" + addressName);
			// city name => city id
			String addressId = CityPaser.getIdByName(addressName);
			result.put("data", weatherService.getNextFiveDaysWeather(addressId));
			result.put("errorcode", "0");
		}
		catch(Exception e){
			result.put("message", "获取天气失败");
			result.put("errorcode", "-1");
		}  
		return result;
	}

	@ResponseBody
	@RequestMapping(value="/getPast")
	public JSONObject  getPast(HttpServletRequest request, @RequestBody Map<String,String> map) {
		JSONObject result= new JSONObject();
		try{
			String addressName = map.get("city");
			System.out.println("/getPast:" + addressName);
			// city name => city id
			String addressId = CityPaser.getIdByName(addressName);
			result.put("data", weatherService.getPrevFiveDaysWeather(addressId));
			result.put("errorcode", "0");
		}
		catch(Exception e){
			result.put("message", "获取天气失败");
			result.put("errorcode", "-1");
		}  
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/getCitys")
	public JSONObject getCitys(HttpServletRequest request/*, @RequestBody Map<String,String> map*/) {
		JSONObject result= new JSONObject();
		try{
			System.out.println("/getCitys:");
			result.put("data", CityPaser.getCityNames());
			result.put("errorcode", "0");
		}
		catch(Exception e){
			result.put("message", "获取城市信息失败");
			result.put("errorcode", "-1");
		}
		return result;
	}
	
	
}
