package team.cloudy.pojo;

/**
 * @author liuwang  
 * @date 2018��10��26�� ����4:46:44  
 * @version 1.0  
 * @TODO ��������
 */
public class Weather {
	private String address_time;// �ص�id_year_month_day
	private String WEP;			// ����
	private String WIN_D;		// ����
	private String WIN_S;		// ����
	private String TEM_MIN;		// ����¶�
	private String TEM_MAX;		// ����¶�
	private String RHU;			// ���ʪ��
	
	public Weather(){ }
	
	public Weather(String address_time, String wEP, String wIN_D, String wIN_S, String tEM_MIN, String tEM_MAX,
			String rHU) {
		this.address_time = address_time;
		WEP = wEP;
		WIN_D = wIN_D;
		WIN_S = wIN_S;
		TEM_MIN = tEM_MIN;
		TEM_MAX = tEM_MAX;
		RHU = rHU;
	}
	
	public String getAddress_time() { return address_time; }
	
	public void setAddress_time(String address_time) { this.address_time = address_time; }
	
	public String getWEP() { return WEP; }
	
	public void setWEP(String wEP) { WEP = wEP; }
	
	public String getWIN_D() { return WIN_D; }
	
	public void setWIN_D(String wIN_D) { WIN_D = wIN_D; }
	
	public String getWIN_S() { return WIN_S; }
	
	public void setWIN_S(String wIN_S) { WIN_S = wIN_S; }
	
	public String getTEM_MIN() { return TEM_MIN; }
	
	public void setTEM_MIN(String tEM_MIN) { TEM_MIN = tEM_MIN; }
	
	public String getTEM_MAX() { return TEM_MAX; }
	
	public void setTEM_MAX(String tEM_MAX) { TEM_MAX = tEM_MAX; }
	
	public String getRHU() { return RHU; }
	
	public void setRHU(String rHU) { RHU = rHU; }
	
	public void display(){
		System.out.println(address_time + " " + 
			WEP + " " + 
			WIN_D + " " + 
			WIN_S + " " +
			TEM_MIN + " " +
			TEM_MAX + " " + 
			RHU
		);
	}
}
