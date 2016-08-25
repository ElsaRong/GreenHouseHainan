package com.greenhouse.networkservice;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/** 
* class <code>NetworkManager</code> 包含了应用中所有与系应用网络环境
* 相关的方法。包括：判断当前网络情况(wifi/3g/未联网)，获取当前手机ip，组装udp广播包
* 
* 所有相关方法都要逐步迁移到这里
* 
* @author       Elsa 
* @Email        elsarong715@gmail.com
* @date         2015年11月9日 下午4:43:37 
* @version      1.0  
*/
public class NetworkManager {

	private static final String TAG = "NetworkManager.java";
	
	public static final int NETWORK_NONE = 0;
	public static final int NETWORK_WIFI = 1;
	public static final int NETWORK_MOBILE = 2;
	
	/**
	 * 获得当前网络状况，如果网络状况发生变化并将影响应用正常，则发广播。广播将是全局的。
	 * 
	 * @param context
	 * @return
	 */
	public static int getNetworkState(Context context) {
		ConnectivityManager connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		if(state == State.CONNECTED || state == State.CONNECTING) {
			return NETWORK_WIFI;
		}		
		// 这里为什么是重新get一个networkstate然后判断，为什么是get一次后if－elseif判断？
		state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		if(state == State.CONNECTED || state == State.CONNECTING) {
			return NETWORK_MOBILE;
		}
		return NETWORK_NONE;
	}
	
	
	
	public static String getLocalIpAddress() {  
        try { 
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {  
                        NetworkInterface intf = en.nextElement();  
                       for (Enumeration<InetAddress> enumIpAddr = intf  
                                .getInetAddresses(); enumIpAddr.hasMoreElements();) {  
                            InetAddress inetAddress = enumIpAddr.nextElement();  
                            if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {  
                            return inetAddress.getHostAddress().toString();  
                            }  
                       }  
                    }  
                } catch (SocketException ex) {  
                    Log.e("WifiPreference IpAddress", ex.toString());  
                }
             return null;  
	} 
	
	public static String getSSid(Context context) {
		WifiManager wm = (WifiManager)context.getSystemService(context.WIFI_SERVICE);
		if (wm != null) {
			WifiInfo wi = wm.getConnectionInfo();
			if (wi != null) {
				String s = wi.getSSID();
				if (s.length() > 2 && s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"') {
					return s.substring(1, s.length() - 1);
				}
			}
		}
		return "";
	}
	
}