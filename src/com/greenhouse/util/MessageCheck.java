package com.greenhouse.util;

/** 
* 
* @author       Elsa 
* @Email        elsarong715@gmail.com
* @date         2015/11/24 AM 10:16:48 
* @version      1.0  
*/

public class MessageCheck {
	
	public static boolean CheckQuer (String s) {
		boolean flag = false;
		if (s.indexOf(Const.QUER_STATISTIC) != -1) {
			flag = true;
		}
		else {
			flag = false;		
		}
		return flag;	
	}
	
	public static boolean CheckTail (String s) {
		boolean flag = false;
		if (s.indexOf(Const.WAN) != -1) {
			flag = true;
		}
		else {
			flag = false;		
		}
		return flag;	
	}
	
	public static boolean CheckQuerTail (String s) {
		boolean flag = false;
		if (s.indexOf(Const.WANA_STATISTIC) != -1) {
			flag = true;
		}
		else {
			flag = false;		
		}
		return flag;	
	}

	public static boolean CheckBackPart(String s) {
		boolean flag = false;
		if (s.indexOf(Const.QUER_STATISTIC) > s.indexOf(Const.WANA_STATISTIC)) {
			flag = true;
		}
		else {
			flag = false;		
		}
		return flag;
	}
	
	public static boolean CheckShortStatistic(String s) {
		boolean flag = false;
		if (s.indexOf(Const.QUER_STATISTIC) < s.indexOf(Const.WANA_STATISTIC) && s.indexOf(",") != -1) {
			flag = true;
		}
		else {
			flag = false;		
		}
		return flag;
	}
	
	
	/**
	 * 报头HFUT和报尾WANG校验，该流必须以HFUT开头，而且不包含quer功能字
	 * @param s
	 */
	public static boolean fromController(String s) {
		if (s.indexOf(Const.HFUT) == 0 && s.indexOf(Const.WAN) != -1 && s.indexOf(Const.QUER_STATISTIC) == -1) {
			return true;
		}
		else {
			return false;			
		}
	}
	
//	public static boolean fromController(String s) {
//		if (s.indexOf(Const.HFUT) == 0 && s.indexOf(Const.WAN) != -1 && s.indexOf(Const.QUER_STATISTIC) == -1) {
//			return true;
//		}
//		else {
//			return false;			
//		}
//	}
	
	public static boolean fromServer(String s) {
		if (s.indexOf("STAT") != -1 || s.indexOf("HAVE") != -1 || s.indexOf("SENS") != -1 || s.indexOf("BUND") != -1
				|| s.indexOf("BUUU") != -1 || s.indexOf("TASK") != -1) {
			return false;
		} else {
			return true;
		}
		
	}
	

	

}

