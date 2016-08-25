package com.greenhouse.specialversion;

import com.greenhouse.ui.JackFragmentSwitchTest;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			2016年4月15日上午10:25:41 
* @version		1.0  
* @description			 
*/
public class LockCheck {
	
	public static boolean CheckLockStateAccordingMac(int id, int state, String mac) {
		boolean b = true;
		if (mac.equals("ACCF236FA948")) {
			b = CheckLockState1(id,state);
		} else if (mac.equals("ACCF2357F44E")) {
			b = CheckLockState2(id,state);
		}
		return b;
	}
	
	public static boolean CheckLockState2(int id, int state) {
		int lockstate;
		boolean b = true;
		String name;
		switch (id) {
		case 1:
			name = JackFragmentSwitchTest.jacks.get(id-1).getName();
			lockstate = JackFragmentSwitchTest.jacks.get(id-1).getSwitchstate();//互锁的插座的状态
			if (lockstate == state) {//如果互锁的插座当前状态lockstate（1）＝要执行的状态（1）
//				ToastUtil.TextToastShort(context, "请确认 " + name + " 是否关闭");
				b = false;
			}
			break;
		case 3:
			name = JackFragmentSwitchTest.jacks.get(id-1).getName();
			lockstate = JackFragmentSwitchTest.jacks.get(id-1).getSwitchstate();
			if (lockstate == state) {
//				ToastUtil.TextToastShort(context, "请确认 " + name + " 是否关闭");
				b = false;
			}
			break;
		case 5:
			name = JackFragmentSwitchTest.jacks.get(id-1).getName();
			lockstate = JackFragmentSwitchTest.jacks.get(id-1).getSwitchstate();
			if (lockstate == state) {
//				ToastUtil.TextToastShort(context, "请确认 " + name + " 是否关闭");
				b = false;
			}
			break;
		case 7:
			name = JackFragmentSwitchTest.jacks.get(id-1).getName();
			lockstate = JackFragmentSwitchTest.jacks.get(id-1).getSwitchstate();
			if (lockstate == state) {
//				ToastUtil.TextToastShort(context, "请确认 " + name + " 是否关闭");
				b = false;
			}
			break;
		case 9:
			name = JackFragmentSwitchTest.jacks.get(id-1).getName();
			lockstate = JackFragmentSwitchTest.jacks.get(id-1).getSwitchstate();
			if (lockstate == state) {
//				ToastUtil.TextToastShort(context, "请确认 " + name + " 是否关闭");
				b = false;
			}
			break;
		case 11:
			name = JackFragmentSwitchTest.jacks.get(id-1).getName();
			lockstate = JackFragmentSwitchTest.jacks.get(id-1).getSwitchstate();
			if (lockstate == state) {
//				ToastUtil.TextToastShort(context, "请确认 " + name + " 是否关闭");
				b = false;
			}
			break;
		case 0:
			name = JackFragmentSwitchTest.jacks.get(id+1).getName();
			lockstate = JackFragmentSwitchTest.jacks.get(id+1).getSwitchstate();//互锁的插座的状态
			if (lockstate == state) {//如果互锁的插座当前状态lockstate（1）＝要执行的状态（1）
//				ToastUtil.TextToastShort(context, "请确认 " + name + " 是否关闭");
				b = false;
			}
			break;
		case 2:
			name = JackFragmentSwitchTest.jacks.get(id+1).getName();
			lockstate = JackFragmentSwitchTest.jacks.get(id+1).getSwitchstate();
			if (lockstate == state) {
//				ToastUtil.TextToastShort(context, "请确认 " + name + " 是否关闭");
				b = false;
			}
			break;
		case 4:
			name = JackFragmentSwitchTest.jacks.get(id+1).getName();
			lockstate = JackFragmentSwitchTest.jacks.get(id+1).getSwitchstate();
			if (lockstate == state) {
//				ToastUtil.TextToastShort(context, "请确认 " + name + " 是否关闭");
				b = false;
			}
			break;
		case 6:
			name = JackFragmentSwitchTest.jacks.get(id+1).getName();
			lockstate = JackFragmentSwitchTest.jacks.get(id+1).getSwitchstate();
			if (lockstate == state) {
//				ToastUtil.TextToastShort(context, "请确认 " + name + " 是否关闭");
				b = false;
			}
			break;
		case 8:
			name = JackFragmentSwitchTest.jacks.get(id+1).getName();
			lockstate = JackFragmentSwitchTest.jacks.get(id+1).getSwitchstate();
			if (lockstate == state) {
//				ToastUtil.TextToastShort(context, "请确认 " + name + " 是否关闭");
				b = false;
			}
			break;
		case 10:
			name = JackFragmentSwitchTest.jacks.get(id+1).getName();
			lockstate = JackFragmentSwitchTest.jacks.get(id+1).getSwitchstate();
			if (lockstate == state) {
//				ToastUtil.TextToastShort(context, "请确认 " + name + " 是否关闭");
				b = false;
			}
			break;
		}
		return b;
	}
	
	
	public static boolean CheckLockState1(int id, int state) {
		int lockstate;
		boolean b = true;
		String name;
		switch (id) {
		case 1:
			name = JackFragmentSwitchTest.jacks.get(id-1).getName();
			lockstate = JackFragmentSwitchTest.jacks.get(id-1).getSwitchstate();//互锁的插座的状态
			if (lockstate == state) {//如果互锁的插座当前状态lockstate（1）＝要执行的状态（1）
//				ToastUtil.TextToastShort(context, "请确认 " + name + " 是否关闭");
				b = false;
			}
			break;
		case 3:
			name = JackFragmentSwitchTest.jacks.get(id-1).getName();
			lockstate = JackFragmentSwitchTest.jacks.get(id-1).getSwitchstate();
			if (lockstate == state) {
//				ToastUtil.TextToastShort(context, "请确认 " + name + " 是否关闭");
				b = false;
			}
			break;
		case 5:
			name = JackFragmentSwitchTest.jacks.get(id-1).getName();
			lockstate = JackFragmentSwitchTest.jacks.get(id-1).getSwitchstate();
			if (lockstate == state) {
//				ToastUtil.TextToastShort(context, "请确认 " + name + " 是否关闭");
				b = false;
			}
			break;
		case 7:
			name = JackFragmentSwitchTest.jacks.get(id-1).getName();
			lockstate = JackFragmentSwitchTest.jacks.get(id-1).getSwitchstate();
			if (lockstate == state) {
//				ToastUtil.TextToastShort(context, "请确认 " + name + " 是否关闭");
				b = false;
			}
			break;
		case 9:
			name = JackFragmentSwitchTest.jacks.get(id-1).getName();
			lockstate = JackFragmentSwitchTest.jacks.get(id-1).getSwitchstate();
			if (lockstate == state) {
//				ToastUtil.TextToastShort(context, "请确认 " + name + " 是否关闭");
				b = false;
			}
			break;
		case 11:
			name = JackFragmentSwitchTest.jacks.get(id-1).getName();
			lockstate = JackFragmentSwitchTest.jacks.get(id-1).getSwitchstate();
			if (lockstate == state) {
//				ToastUtil.TextToastShort(context, "请确认 " + name + " 是否关闭");
				b = false;
			}
			break;
	
		case 0:
			name = JackFragmentSwitchTest.jacks.get(id+1).getName();
			lockstate = JackFragmentSwitchTest.jacks.get(id+1).getSwitchstate();//互锁的插座的状态
			if (lockstate == state) {//如果互锁的插座当前状态lockstate（1）＝要执行的状态（1）
//				ToastUtil.TextToastShort(context, "请确认 " + name + " 是否关闭");
				b = false;
			}
			break;
		case 2:
			name = JackFragmentSwitchTest.jacks.get(id+1).getName();
			lockstate = JackFragmentSwitchTest.jacks.get(id+1).getSwitchstate();
			if (lockstate == state) {
//				ToastUtil.TextToastShort(context, "请确认 " + name + " 是否关闭");
				b = false;
			}
			break;
		case 4:
			name = JackFragmentSwitchTest.jacks.get(id+1).getName();
			lockstate = JackFragmentSwitchTest.jacks.get(id+1).getSwitchstate();
			if (lockstate == state) {
//				ToastUtil.TextToastShort(context, "请确认 " + name + " 是否关闭");
				b = false;
			}
			break;
		case 6:
			name = JackFragmentSwitchTest.jacks.get(id+1).getName();
			lockstate = JackFragmentSwitchTest.jacks.get(id+1).getSwitchstate();
			if (lockstate == state) {
//				ToastUtil.TextToastShort(context, "请确认 " + name + " 是否关闭");
				b = false;
			}
			break;
		case 8:
			name = JackFragmentSwitchTest.jacks.get(id+1).getName();
			lockstate = JackFragmentSwitchTest.jacks.get(id+1).getSwitchstate();
			if (lockstate == state) {
//				ToastUtil.TextToastShort(context, "请确认 " + name + " 是否关闭");
				b = false;
			}
			break;
		case 10:
			name = JackFragmentSwitchTest.jacks.get(id+1).getName();
			lockstate = JackFragmentSwitchTest.jacks.get(id+1).getSwitchstate();
			if (lockstate == state) {
//				ToastUtil.TextToastShort(context, "请确认 " + name + " 是否关闭");
				b = false;
			}
			break;
		}
		return b;
	}





}
