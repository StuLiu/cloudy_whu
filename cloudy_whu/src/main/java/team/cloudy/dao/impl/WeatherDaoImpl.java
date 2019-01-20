package team.cloudy.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.springframework.stereotype.Repository;

import team.cloudy.dao.WeatherDao;
import team.cloudy.pojo.Weather;
import team.cloudy.utils.HBaseApp;

@Repository
public class WeatherDaoImpl implements WeatherDao {
	
	private final String TABLE_NAME = "t_weather";
	private final String WEATHER = "weather";
	private final String[] COLS = {"WEP","WIN_D","WIN_S","TEM_MIN","TEM_MAX","RHU"};
	private HBaseApp hba;
	
	public WeatherDaoImpl(){ hba = new HBaseApp(); }
	
	@Override
	public void update(Weather weather) throws IOException {
		// TODO Auto-generated method stub
		List<String> cols = Arrays.asList(COLS);
		List<String> vals = new ArrayList<String>();
		vals.add(weather.getWEP());
		vals.add(weather.getWIN_D());
		vals.add(weather.getWIN_S());
		vals.add(weather.getTEM_MIN());
		vals.add(weather.getTEM_MAX());
		vals.add(weather.getRHU());
		hba.insertRow(TABLE_NAME, weather.getAddress_time(), WEATHER, cols, vals);
	}

	@Override
	public Weather query(String addressId, Calendar date) throws IOException {
		// TODO get weather today
		System.out.println("WeatherDao.query");
		String rowkey = addressId + "_" + date.get(Calendar.YEAR) + "_" + 
				(date.get(Calendar.MONTH) + 1)+ "_" + date.get(Calendar.DAY_OF_MONTH);
		Result result = hba.getData(TABLE_NAME, rowkey);
		if(result == null){
			return new Weather();
		}
		else{
			Cell[] cells = result.rawCells();
			Weather weather = new Weather( rowkey,
					new String(CellUtil.cloneValue(cells[3])),
					new String(CellUtil.cloneValue(cells[4])),
					new String(CellUtil.cloneValue(cells[5])),
					new String(CellUtil.cloneValue(cells[2])),
					new String(CellUtil.cloneValue(cells[1])),
					new String(CellUtil.cloneValue(cells[0])) ); 
			return weather;
		}
	}

	@Override
	public List<Weather> scan(String addressId, int days) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("WeatherDao.scan");
		int dd = (days > 0) ? 1:-1;
		List<Weather> weathers = new ArrayList<Weather>();
		Calendar date = Calendar.getInstance();		// 获取当前时间日历
		for(int i = 0; i < (int)Math.abs(days); ++i){
			Weather weather = this.query(addressId, date);
			weather.display();
			weathers.add(weather);
			date.add(Calendar.DATE, dd);
		}
		return weathers;
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Calendar now = Calendar.getInstance();  
		System.out.println("年: " + now.get(Calendar.YEAR));  
		System.out.println("月: " + (now.get(Calendar.MONTH) + 1) + "");  
		System.out.println("日: " + now.get(Calendar.DAY_OF_MONTH)); 
		WeatherDao wd = new WeatherDaoImpl();
		wd.scan("101250201", 5);
	}

}
