package com.greenhouse.database;

import java.util.List;
import android.test.AndroidTestCase;
import android.util.Log;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			2016年2月21日下午1:29:36 
* @version		1.0  
* @description			 
*/
public class ControllerServiceTest extends AndroidTestCase{
	
	private static final String TAG = "ControllerServiceTest.java";

	public void testInit() throws Throwable {
		ControllerService controllerService = new ControllerService(this.getContext());//Context通信
		controllerService.init("hfut", "1992.9.29");
		controllerService.init("hfut1", "1992.9.29");
		controllerService.init("hfut2", "1992.9.29");
		controllerService.init("hfut3", "1992.9.29");
		/*controllerService.init("hfut", "1992.9.29");
		controllerService.init("hfut1", "1992.9.29");
		controllerService.init("hfut2", "1992.9.29");
		controllerService.init("hfut3", "1992.9.29");*/
	}
	
	public void testGetAllMac() throws Throwable {
		ControllerService controllerService = new ControllerService(this.getContext());
		List<String> macs = controllerService.getAllMac();
		for(int i = 0; i < macs.size(); i++){
			Log.d(TAG, macs.get(i));//打印Log.v(all)\d(debug)\i(information)\w(warning)\e(error);
		}
	}
	
	/*public void testGetIp() throws Throwable
	{
		ControllerService controllerService = new ControllerService(this.getContext());
		String mac1="hfut";
		String Ips=controllerService.queryIp(mac1);
		Log.d(TAG, Ips);
	}*/
}
