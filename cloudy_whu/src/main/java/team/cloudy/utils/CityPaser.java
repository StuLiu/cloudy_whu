package team.cloudy.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityPaser {
	private static Map<String, String> id_name;
	private static Map<String, String> name_id;
	static {
		id_name = new HashMap<String, String>();
		name_id = new HashMap<String, String>();
		
		id_name.put("101201601", "仙桃");
		id_name.put("101200301", "鄂州");
		id_name.put("101200501", "黄冈");
		id_name.put("101200601", "黄石");
		id_name.put("101201401", "荆门");
		id_name.put("101200801", "荆州");
		id_name.put("101201701", "潜江");
		id_name.put("101201201", "神农架林区");
		id_name.put("101201101", "十堰");
		id_name.put("101201301", "随州");
		id_name.put("101201501", "天门");
		id_name.put("101200701", "咸宁");
		id_name.put("101200202", "襄阳");
		id_name.put("101200401", "孝感");
		id_name.put("101200901", "宜昌");
		id_name.put("101201001", "恩施");
		id_name.put("101250101", "长沙");
		id_name.put("101251101", "张家界");
		id_name.put("101250601", "常德");
		id_name.put("101250501", "郴州");
		id_name.put("101250401", "衡阳");
		id_name.put("101251201", "怀化");
		id_name.put("101250801", "娄底");
		id_name.put("101250901", "邵阳");
		id_name.put("101250201", "湘潭");
		id_name.put("101251509", "湘西");
		id_name.put("101250700", "益阳");
		id_name.put("101251401", "永州");
		id_name.put("101251001", "岳阳");
		id_name.put("101250301", "株洲");
		
		for(String id : id_name.keySet()){
//			System.out.println(id+id_name.get(id));
			name_id.put(id_name.get(id), id);
		}
	}
	
	public static String getNameById(String id){
		return id_name.get(id);
	}
	
	public static String getIdByName(String name){
		return name_id.get(name);
	}
	
	public static List<String> getCityNames(){
		return new ArrayList<String>(id_name.values());
	}
	
	public static List<String> getCityIds(){
		return new ArrayList<String>(id_name.keySet());
	}
	
	public static Map<String, String> getCityPairs(){
		return id_name;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(CityPaser.getNameById( "101250201" ));
		System.out.println(CityPaser.getIdByName( "湘潭" ));
		System.out.println(CityPaser.getCityNames());
	}

}
