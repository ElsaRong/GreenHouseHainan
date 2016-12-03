package com.greenhouse.specialversion;

import java.util.List;
import java.util.Map;
import com.greenhouse.ui.JackFragmentShowinfo;
import com.greenhouse.ui.JackFragmentSwitchTest;
import com.greenhouse.util.GreenHouseApplication;
import com.greenhouse.util.ToastUtil;

import android.content.Context;
import android.util.Log;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			2016-4-15 10:25:41 
* @version		1.0  
* @description			 
*/



public class LockCheck {
	
	private static final String TAG = "LockCheck.java";
	
	public static boolean CheckLockStateAccordingMac(int id, int state, String mac) {
		boolean b = true;
		if (mac.equals("ACCF236FA948")) {
			b = CheckLockState1(id,state);
		} else if (mac.equals("ACCF2357F44E")) {
			b = CheckLockState2(id,state);
		}
		return b;
	}
	
	private static Context context = GreenHouseApplication.getContext();
//	
//	public static boolean CheckLockStateAccordingMac(int id, int state, String mac) {
//		boolean b = true;
//		if (mac.equals(Launcher.selectMac+"")) {
//			b = CheckLockState1(id,state);
//		} 
//		return b;
//	}
//	
//	
//	
	public static boolean CheckLockState2(int id, int state) {
		int lockstate;
		boolean b = true;
		String name = "";
		switch (id) {
		case 1:
			name = JackFragmentSwitchTest.jackSwitchInfoList.get(id-1).getName();
			lockstate = JackFragmentSwitchTest.jackSwitchInfoList.get(id-1).getSwitchstate();
			if (lockstate == state) {
				ToastUtil.TextToastLong(context, "请确认" + name + "是否关闭");
				b = false;
			}
			break;
		case 3:
			name = JackFragmentSwitchTest.jackSwitchInfoList.get(id-1).getName();
			lockstate = JackFragmentSwitchTest.jackSwitchInfoList.get(id-1).getSwitchstate();
			if (lockstate == state) {
				ToastUtil.TextToastLong(context, "请确认" + name + "是否关闭");
				b = false;
			}
			break;
		case 5:
			name = JackFragmentSwitchTest.jackSwitchInfoList.get(id-1).getName();
			lockstate = JackFragmentSwitchTest.jackSwitchInfoList.get(id-1).getSwitchstate();
			if (lockstate == state) {
				ToastUtil.TextToastLong(context, "请确认" + name + "是否关闭");
				b = false;
			}
			break;
		case 7:
			name = JackFragmentSwitchTest.jackSwitchInfoList.get(id-1).getName();
			lockstate = JackFragmentSwitchTest.jackSwitchInfoList.get(id-1).getSwitchstate();
			if (lockstate == state) {
				ToastUtil.TextToastLong(context, "请确认" + name + "是否关闭");
				b = false;
			}
			break;
		case 9:
			name = JackFragmentSwitchTest.jackSwitchInfoList.get(id-1).getName();
			lockstate = JackFragmentSwitchTest.jackSwitchInfoList.get(id-1).getSwitchstate();
			if (lockstate == state) {
				ToastUtil.TextToastLong(context, "请确认" + name + "是否关闭");
				b = false;
			}
			break;
		case 11:
			name = JackFragmentSwitchTest.jackSwitchInfoList.get(id-1).getName();
			lockstate = JackFragmentSwitchTest.jackSwitchInfoList.get(id-1).getSwitchstate();
			if (lockstate == state) {
				ToastUtil.TextToastLong(context, "请确认" + name + "是否关闭");
				b = false;
			}
			break;
		case 0:
			name = JackFragmentSwitchTest.jackSwitchInfoList.get(id+1).getName();
			lockstate = JackFragmentSwitchTest.jackSwitchInfoList.get(id+1).getSwitchstate();//�����Ĳ�����״̬
			if (lockstate == state) {//��������Ĳ�����ǰ״̬lockstate��1����Ҫִ�е�״̬��1��
				ToastUtil.TextToastLong(context, "请确认" + name + "是否关闭");
				b = false;
			}
			break;
		case 2:
			name = JackFragmentSwitchTest.jackSwitchInfoList.get(id+1).getName();
			lockstate = JackFragmentSwitchTest.jackSwitchInfoList.get(id+1).getSwitchstate();
			if (lockstate == state) {
				ToastUtil.TextToastLong(context, "请确认" + name + "是否关闭");
				b = false;
			}
			break;
		case 4:
			name = JackFragmentSwitchTest.jackSwitchInfoList.get(id+1).getName();
			lockstate = JackFragmentSwitchTest.jackSwitchInfoList.get(id+1).getSwitchstate();
			if (lockstate == state) {
				ToastUtil.TextToastLong(context, "请确认" + name + "是否关闭");
				b = false;
			}
			break;
		case 6:
			name = JackFragmentSwitchTest.jackSwitchInfoList.get(id+1).getName();
			lockstate = JackFragmentSwitchTest.jackSwitchInfoList.get(id+1).getSwitchstate();
			if (lockstate == state) {
				ToastUtil.TextToastLong(context, "请确认" + name + "是否关闭");
				b = false;
			}
			break;
		case 8:
			name = JackFragmentSwitchTest.jackSwitchInfoList.get(id+1).getName();
			lockstate = JackFragmentSwitchTest.jackSwitchInfoList.get(id+1).getSwitchstate();
			if (lockstate == state) {
				ToastUtil.TextToastLong(context, "请确认" + name + "是否关闭");
				b = false;
			}
			break;
		case 10:
			name = JackFragmentSwitchTest.jackSwitchInfoList.get(id+1).getName();
			lockstate = JackFragmentSwitchTest.jackSwitchInfoList.get(id+1).getSwitchstate();
			if (lockstate == state) {
				ToastUtil.TextToastLong(context, "请确认" + name + "是否关闭");
				b = false;
			}
			break;
		}
		return b;
	}
//	
//	
//	/**
//	 * @Title:       CheckLockState1
//	 * @description: TODO 一号柜开关互锁
//	 * @param        @param id
//	 * @param        @param state
//	 * @param        @return
//	 * @return       boolean
//	 * @throws
//	 * @author       Elsa elsarong715@gmail.com
//	 * @data         Aug 27, 2016, 7:12:19 PM
//	 */
	public static boolean CheckLockState1(int id, int state) {
		int lockstate;
		boolean b = true;
		String name;
		switch (id) {
		case 1:
			name = JackFragmentSwitchTest.jackSwitchInfoList.get(id-1).getName();
			lockstate = JackFragmentSwitchTest.jackSwitchInfoList.get(id-1).getSwitchstate();
			Log.e(TAG, "检查上一位状态：id="+(id-1)+",current state="+lockstate);
			if (lockstate == state) {
				ToastUtil.TextToastLong(context, "请确认 \"" + name + "\" 是否关闭");
				b = false;
			}
			break;
		case 3:
			name = JackFragmentSwitchTest.jackSwitchInfoList.get(id-1).getName();
			lockstate = JackFragmentSwitchTest.jackSwitchInfoList.get(id-1).getSwitchstate();
			Log.e(TAG, "id="+(id-1)+",current state="+lockstate);
			if (lockstate == state) {
				ToastUtil.TextToastLong(context, "请确认 \"" + name + "\" 是否关闭");
				b = false;
			}
			break;
		case 5:
			name = JackFragmentShowinfo.jackInfoList.get(id-1).getName();
			lockstate = JackFragmentShowinfo.jackInfoList.get(id-1).getSwitchstate();
			if (lockstate == state) {
				ToastUtil.TextToastLong(context, "请确认 \"" + name + "\" 是否关闭");
				b = false;
			}
			break;
		case 7:
			name = JackFragmentShowinfo.jackInfoList.get(id-1).getName();
			lockstate = JackFragmentShowinfo.jackInfoList.get(id-1).getSwitchstate();
			if (lockstate == state) {
				ToastUtil.TextToastLong(context, "请确认 \"" + name + "\" 是否关闭");
				b = false;
			}
			break;
		case 9:
			name = JackFragmentShowinfo.jackInfoList.get(id-1).getName();
			lockstate = JackFragmentShowinfo.jackInfoList.get(id-1).getSwitchstate();
			if (lockstate == state) {
				ToastUtil.TextToastLong(context, "请确认 \"" + name + "\" 是否关闭");
				b = false;
			}
			break;
		case 11:
			name = JackFragmentShowinfo.jackInfoList.get(id-1).getName();
			lockstate = JackFragmentShowinfo.jackInfoList.get(id-1).getSwitchstate();
			if (lockstate == state) {
				ToastUtil.TextToastLong(context, "请确认 \"" + name + "\" 是否关闭");
				b = false;
			}
			break;
	
		case 0:
			name = JackFragmentShowinfo.jackInfoList.get(id+1).getName();
			lockstate = JackFragmentShowinfo.jackInfoList.get(id+1).getSwitchstate();//�����Ĳ�����״̬
			if (lockstate == state) {//��������Ĳ�����ǰ״̬lockstate��1����Ҫִ�е�״̬��1��
				ToastUtil.TextToastLong(context, "请确认 \"" + name + "\" 是否关闭");
				b = false;
			}
			break;
		case 2:
			name = JackFragmentShowinfo.jackInfoList.get(id+1).getName();
			lockstate = JackFragmentShowinfo.jackInfoList.get(id+1).getSwitchstate();
			if (lockstate == state) {
				ToastUtil.TextToastLong(context, "请确认 \"" + name + "\" 是否关闭");
				b = false;
			}
			break;
		case 4:
			name = JackFragmentShowinfo.jackInfoList.get(id+1).getName();
			lockstate = JackFragmentShowinfo.jackInfoList.get(id+1).getSwitchstate();
			if (lockstate == state) {
				ToastUtil.TextToastLong(context, "请确认 \"" + name + "\" 是否关闭");
				b = false;
			}
			break;
		case 6:
			name = JackFragmentShowinfo.jackInfoList.get(id+1).getName();
			lockstate = JackFragmentShowinfo.jackInfoList.get(id+1).getSwitchstate();
			if (lockstate == state) {
				ToastUtil.TextToastLong(context, "请确认 \"" + name + "\" 是否关闭");
				b = false;
			}
			break;
		case 8:
			name = JackFragmentShowinfo.jackInfoList.get(id+1).getName();
			lockstate = JackFragmentShowinfo.jackInfoList.get(id+1).getSwitchstate();
			if (lockstate == state) {
				ToastUtil.TextToastLong(context, "请确认 \"" + name + "\" 是否关闭");
				b = false;
			}
			break;
		case 10:
			name = JackFragmentShowinfo.jackInfoList.get(id+1).getName();
			lockstate = JackFragmentShowinfo.jackInfoList.get(id+1).getSwitchstate();
			if (lockstate == state) {
				ToastUtil.TextToastLong(context, "请确认 \"" + name + "\" 是否关闭");
				b = false;
			}
			break;
		}
		return b;
	}
//	
	public static boolean checkTimerChosedJack(List<Map<String, String>> multiChosedJack) {
		boolean b = true;
//		
//		int[] binChosedJack48 = new int[48];
//		
//		Iterator<Map<String, String>> iterator = multiChosedJack.iterator();
//		String extractJackId = "";
//		
//		for(int i=0; i<48; i++){
//			binChosedJack48[i] = 0;
//		}
//		
//		while(iterator.hasNext()) {
//			Object object = iterator.next();
//			extractJackId = object.toString();			
//			int startIndex = extractJackId.indexOf("=") + 1;
//			int endIndex = extractJackId.indexOf("}");
//			int chosedJackId = Integer.parseInt(extractJackId.substring(startIndex, endIndex));
//			binChosedJack48[chosedJackId - 1] = 1;	
//		}		
//		
//		if (binChosedJack48[0] == 1 && binChosedJack48[1] == 1) {
//			ToastUtil.TextToastLong(context, JackFragmentShowinfo.jacks.get(0).getName() + "和"
//					+ JackFragmentShowinfo.jacks.get(1).getName() + " 不能同时动作");
//			b=false;
//		} else if (binChosedJack48[2] == 1 && binChosedJack48[3] == 1) {
//			ToastUtil.TextToastLong(context, JackFragmentShowinfo.jacks.get(2).getName() + "和"
//					+ JackFragmentShowinfo.jacks.get(3).getName() + " 不能同时动作");
//			b=false;
//		} else if (binChosedJack48[4] == 1 && binChosedJack48[5] == 1) {
//			ToastUtil.TextToastLong(context, JackFragmentShowinfo.jacks.get(4).getName() + "和"
//					+ JackFragmentShowinfo.jacks.get(5).getName() + " 不能同时动作");
//			b=false;
//		} else if (binChosedJack48[6] == 1 && binChosedJack48[7] == 1) {
//			ToastUtil.TextToastLong(context, JackFragmentShowinfo.jacks.get(6).getName() + "和"
//					+ JackFragmentShowinfo.jacks.get(7).getName() + " 不能同时动作");
//			b=false;
//		} else if (binChosedJack48[8] == 1 && binChosedJack48[9] == 1) {
//			ToastUtil.TextToastLong(context, JackFragmentShowinfo.jacks.get(8).getName() + "和"
//					+ JackFragmentShowinfo.jacks.get(9).getName() + " 不能同时动作");
//			b=false;
//		} else if (binChosedJack48[10] == 1 && binChosedJack48[11] == 1) {
//			ToastUtil.TextToastLong(context, JackFragmentShowinfo.jacks.get(10).getName() + "和"
//					+ JackFragmentShowinfo.jacks.get(11).getName() + " 不能同时动作");
//			b=false;
//		} 
//		
		return b;
	}





}
