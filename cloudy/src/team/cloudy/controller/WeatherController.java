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

@Controller
@RequestMapping("/weather")
public class WeatherController {
	
	@Autowired
	private WeatherService weatherService;
	
//	public WeatherController(){
//		weatherService = new WeatherServiceImpl();
//	}
	
	@ResponseBody
	@RequestMapping(value="/getFuture")
	public JSONObject getFuture(HttpServletRequest request, @RequestBody Map<String,String> map) {
		JSONObject result= new JSONObject();
		try{
			String addressName = map.get("city");
			System.out.println("/getFuture:" + addressName);
			// city name => city id
			String addressId = addressName;
//			result.put("data", weatherService.getWeather(addressId, 5));
			result.put("sss", "tianqixinxi");
		}
		catch(Exception e){
			result.put("message", "获取天气信息失败");
		}  
		return result;
	}
	
}
