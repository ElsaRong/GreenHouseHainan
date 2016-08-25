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
* class <code>NetworkManager</code> ������Ӧ����������ϵӦ�����绷��
* ��صķ������������жϵ�ǰ�������(wifi/3g/δ����)����ȡ��ǰ�ֻ�ip����װudp�㲥��
* 
* ������ط�����Ҫ��Ǩ�Ƶ�����
* 
* @author       Elsa 
* @Email        elsarong715@gmail.com
* @date         2015��11��9�� ����4:43:37 
* @version      1.0  
*/
public class NetworkManager {

	private static final String TAG = "NetworkManager.java";
	
	public static final int NETWORK_NONE = 0;
	public static final int NETWORK_WIFI = 1;
	public static final int NETWORK_MOBILE = 2;
	
	/**
	 * ��õ�ǰ����״�����������״�������仯����Ӱ��Ӧ���������򷢹㲥���㲥����ȫ�ֵġ�
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
		// ����Ϊʲô������getһ��networkstateȻ���жϣ�Ϊʲô��getһ�κ�if��elseif�жϣ�
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