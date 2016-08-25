package com.greenhouse.model;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			2016/2/20 PM 1:37:24 
* @version		1.0  
* @description			 
*/
public class Jack {

	private String mac;               //控制器MAC
	private Integer jackId;           //插座编号0-JACK_SUM
	private String name;              //插座名称，初始化为插座＋编号，specialVersion初始化时自动更新名称
	private String drawable;          //插座图标显示
	private Integer switch_state;     //开关状态0or1
	private Integer bund;             //是否绑定任务
	private String sensors;           //绑定的传感器编号,格式为00000000,10100000
//	private Integer Sensortype;传感器类型因为一个插座绑定多个的要求,失去作用
	
	private Integer bundtype1;        //设备类型,即高于or低于门限动作
	private Integer current_value1;   //传感器类型1实时值
	private Integer day_threshold1;   //传感器类型1白天门限
	private Integer night_threshold1; //传感器类型1夜间门限
	
	private Integer bundtype2;
	private Integer current_value2;
	private Integer day_threshold2;
	private Integer night_threshold2;
	
	private Integer bundtype3;
	private Integer current_value3;
	private Integer day_threshold3;
	private Integer night_threshold3;
	
	private Integer bundtype4;
	private Integer current_value4;
	private Integer day_threshold4;
	private Integer night_threshold4;
	
	private Integer bundtype5;
	private Integer current_value5;
	private Integer day_threshold5;
	private Integer night_threshold5;
	
	private Integer bundtype6;
	private Integer current_value6;
	private Integer day_threshold6;
	private Integer night_threshold6;
	
	private Integer bundtype7;
	private Integer current_value7;
	private Integer day_threshold7;
	private Integer night_threshold7;
	
	private String start;
	private String poweron;
	private String poweroff;
	private Integer cycle;
	private String stop;
	
	
	public void setMac(String mac){
		this.mac = mac;
	}
	
	public String getMac() {
		return mac;
	}
	
	public void setJackId(Integer id) {
		this.jackId = id;
	}
	
	public Integer getJackId() {
		return jackId;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setDrawable(String drawable){
		this.drawable = drawable;
	}
	
	public String getDrawable() {
		return drawable;
	}
	
	public void setSwitchstate(Integer state){
		this.switch_state = state;
	}
	
	public Integer getSwitchstate() {
		return switch_state;
	}
	
	public void setBund(Integer bund) {
		this.bund = bund;
	}
	
	public Integer getBund() {
		return bund;
	}
	
	public void setSensors(String sensors){
		this.sensors = sensors;
	}

	public String getSensors() {
		return sensors;
	}

	
	public void setStart(String start){
		this.start = start;
	}
	
	public String getStart() {
		return start;
	}
	
	public void setPoweron(String poweron){
		this.poweron = poweron;
	}
	
	public String getPoweron() {
		return poweron;
	}
	
	public void setPoweroff(String poweroff){
		this.poweroff = poweroff;
	}
	
	public String getPoweroff() {
		return poweroff;
	}
	
	public void setCycle(Integer cycle){
		this.cycle = cycle;
	}
	
	public Integer getCycle() {
		return cycle;
	}
	
	public void setStop(String stop){
		this.stop = stop;
	}
	
	public String getStop() {
		return stop;
	}

	//--------------------------1---------------------------
	public Integer getbundtype1() {
		return bundtype1;
	}

	public void setbundtype1(Integer bundtype1) {
		this.bundtype1 = bundtype1;
	}
	
	public void setCurrentValue1(Integer value){
		this.current_value1 = value;
	}
	
	public Integer getCurrentValue1() {
		return current_value1;
	}
	
	public Integer getDay_threshold1() {
		return day_threshold1;
	}

	public void setDay_threshold1(Integer day_threshold1) {
		this.day_threshold1 = day_threshold1;
	}

	public Integer getNight_threshold1() {
		return night_threshold1;
	}

	public void setNight_threshold1(Integer night_threshold1) {
		this.night_threshold1 = night_threshold1;
	}

	//--------------------------2---------------------------
	public Integer getbundtype2() {
		return bundtype2;
	}

	public void setbundtype2(Integer bundtype2) {
		this.bundtype2 = bundtype2;
	}
	
	public void setCurrentValue2(Integer value){
		this.current_value2 = value;
	}
	
	public Integer getCurrentValue2() {
		return current_value2;
	}
	
	public Integer getDay_threshold2() {
		return day_threshold2;
	}

	public void setDay_threshold2(Integer day_threshold2) {
		this.day_threshold2 = day_threshold2;
	}

	public Integer getNight_threshold2() {
		return night_threshold2;
	}

	public void setNight_threshold2(Integer night_threshold2) {
		this.night_threshold2 = night_threshold2;
	}

	//--------------------------3---------------------------
	public Integer getbundtype3() {
		return bundtype3;
	}

	public void setbundtype3(Integer bundtype3) {
		this.bundtype3 = bundtype3;
	}
	
	public void setCurrentValue3(Integer value){
		this.current_value3 = value;
	}
	
	public Integer getCurrentValue3() {
		return current_value3;
	}
	
	public Integer getDay_threshold3() {
		return day_threshold3;
	}

	public void setDay_threshold3(Integer day_threshold3) {
		this.day_threshold3 = day_threshold3;
	}

	public Integer getNight_threshold3() {
		return night_threshold3;
	}

	public void setNight_threshold3(Integer night_threshold3) {
		this.night_threshold3 = night_threshold3;
	}
	
	//--------------------------4---------------------------
	public Integer getbundtype4() {
		return bundtype4;
	}

	public void setbundtype4(Integer bundtype4) {
		this.bundtype4 = bundtype4;
	}
	
	public void setCurrentValue4(Integer value){
		this.current_value4 = value;
	}
	
	public Integer getCurrentValue4() {
		return current_value4;
	}

	public Integer getDay_threshold4() {
		return day_threshold4;
	}

	public void setDay_threshold4(Integer day_threshold4) {
		this.day_threshold4 = day_threshold4;
	}

	public Integer getNight_threshold4() {
		return night_threshold4;
	}
	
	public void setNight_threshold4(Integer night_threshold4) {
		this.night_threshold4 = night_threshold4;
	}
	
	//--------------------------5---------------------------
	public Integer getbundtype5() {
		return bundtype5;
	}

	public void setbundtype5(Integer bundtype5) {
		this.bundtype5 = bundtype5;
	}
	
	public void setCurrentValue5(Integer value){
		this.current_value5 = value;
	}
	
	public Integer getCurrentValue5() {
		return current_value5;
	}

	public Integer getDay_threshold5() {
		return day_threshold5;
	}

	public void setDay_threshold5(Integer day_threshold5) {
		this.day_threshold5 = day_threshold5;
	}

	public Integer getNight_threshold5() {
		return night_threshold5;
	}

	public void setNight_threshold5(Integer night_threshold5) {
		this.night_threshold5 = night_threshold5;
	}

	//--------------------------6---------------------------
	public Integer getbundtype6() {
		return bundtype6;
	}

	public void setbundtype6(Integer bundtype6) {
		this.bundtype6 = bundtype6;
	}
	
	public void setCurrentValue6(Integer value){
		this.current_value6 = value;
	}
	
	public Integer getCurrentValue6() {
		return current_value6;
	}

	public Integer getDay_threshold6() {
		return day_threshold6;
	}

	public void setDay_threshold6(Integer day_threshold6) {
		this.day_threshold6 = day_threshold6;
	}

	public Integer getNight_threshold6() {
		return night_threshold6;
	}

	public void setNight_threshold6(Integer night_threshold6) {
		this.night_threshold6 = night_threshold6;
	}

	//--------------------------7---------------------------
	public Integer getbundtype7() {
		return bundtype7;
	}

	public void setbundtype7(Integer bundtype7) {
		this.bundtype7 = bundtype7;
	}
	
	public void setCurrentValue7(Integer value){
		this.current_value7 = value;
	}
	
	public Integer getCurrentValue7() {
		return current_value7;
	}

	public Integer getDay_threshold7() {
		return day_threshold7;
	}

	public void setDay_threshold7(Integer day_threshold7) {
		this.day_threshold7 = day_threshold7;
	}

	public Integer getNight_threshold7() {
		return night_threshold7;
	}

	public void setNight_threshold7(Integer night_threshold7) {
		this.night_threshold7 = night_threshold7;
	}

	

	public void setSensortype(Integer sensortype) {
		// TODO �Զ����ɵķ������
		
	}

	public Integer getSensortype() {
		// TODO �Զ����ɵķ������
		return null;
	}
}










