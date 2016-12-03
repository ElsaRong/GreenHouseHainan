/**
 * 
 */package com.greenhouse.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			Nov 24, 201611:07:08 AM 
* @version		1.0  
* @description			 
*/
 public class BatteryReceiver extends BroadcastReceiver { 
	 
		public static PowerManager.WakeLock wakeLock; 
		static Handler handler = new Handler();
	 
	    @Override 
	    public void onReceive(Context context, Intent intent) { 
	        if (Intent.ACTION_BATTERY_OKAY.equals(intent.getAction())) { 
	            Toast.makeText(context, "电量已恢复，可以使用!", Toast.LENGTH_LONG).show(); 
	        } 
	        if (Intent.ACTION_BATTERY_LOW.equals(intent.getAction())) { 
	            Toast.makeText(context, "电量过低，请尽快充电！", Toast.LENGTH_LONG).show(); 
	            keepScreenOff(GreenHouseApplication.getContext());
	        } 
	        if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) { 
	            Bundle bundle = intent.getExtras(); 
	            // 获取当前电量  
	            int current = bundle.getInt("level"); 
	            // 获取总电量  
	            int total = bundle.getInt("scale"); 
	            StringBuffer sb = new StringBuffer(); 
	            sb.append("当前电量为：" + current * 100 / total + "%" + "  "); 
	            // 如果当前电量小于总电量的15%  
	            if (current * 1.0 / total < 0.15) { 
	                sb.append("电量过低，请尽快充电！"); 
	            } else { 
	                sb.append("电量足够，请放心使用！"); 
	            } 
	            Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG).show(); 
	        } 
	 
	    }
	    
	    
	    
	    public static void keepScreenOff(Context context) {
	    	PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
	    	final PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "TAG");
	    	wakeLock.acquire();
	    	//然后定时
	    	handler.postDelayed(new Runnable(){
		    	public void run(){
		    	wakeLock.release();//
		    	}
	    	}, 3000);//延时10秒灭屏
	    }
	    
		public static void keepScreenOn(Context context, boolean on) {
			PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
			wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "==KeepScreenOn==");
		    if (on) {
		        wakeLock.acquire();
		    } else {
		        if (wakeLock != null) {
		            wakeLock.release();
		            wakeLock = null;
		        }
		    }
		}

	 
	} 

