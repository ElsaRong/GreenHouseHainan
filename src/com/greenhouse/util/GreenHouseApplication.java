package com.greenhouse.util;

import com.greenhouse.networkservice.NetworkManager;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/** 
* class <code>NetBroadcastReceiver</code> 将在Application中需要调用应用
* 和系统资源的部分分离出来，写成接口。
* 
* @author       Elsa 
* @Email        elsarong715@gmail.com
* @date         2015年11月9日 下午9:50:07 
* @version      1.0  
*/
public class GreenHouseApplication extends Application{
	
	
	private static GreenHouseApplication mApplication; //有的地方需要application
	private static Context mContext; // 有的地方需要context
	private Handler mainHandler = null;
	
	private static int mNetworkState;
	
	private static String SsidPassword = "";
	
	public void setMainHandler(Handler mainHandler) {
		this.mainHandler = mainHandler;
	}	

	public Handler getMainHandler() {
		return mainHandler;
	}
	
	
	/**
	 * socket连接状态的全局变量有4种值，程序初始化－1，正在连接0，已连接1，连接断开2
	 * 
	 * 应用种有一下基础可以修改该变量的地方：
	 * （1）socket建立连接的位置（已连接／连接断开／正在连接）
	 * （2）应用初始化（－1）
	 * （3）全局网络监听，如果网络变化，立刻置2（连接断开），如果重连，重复（1）
	 */
	private static int SOCKET_CONNECTION_FLAG = -1;

	
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
		InitNetworkParam();
	}
	
    public static Context getContext(){  
        return mContext;  
    } 
	
	public static synchronized Application getApplicationInstance() {
		return mApplication; 		
	}
	
	public void InitNetworkParam() {
		mNetworkState = NetworkManager.getNetworkState(this);
	}
	
	public static int getNetworkState() {
		return mNetworkState;
	}
	
	public static void setNetworkState(int networkState) {
		mNetworkState = networkState;
	}
	
	public static void setPassword(String password) {
		SsidPassword = password;
	}
	
	public static String getPassword() {
		return SsidPassword;
	}

	
	
	public static int getSocketConnectionFlag() {
		return SOCKET_CONNECTION_FLAG;
	}
	
	public static void setSocketConnectionFlag(int flag) {
		SOCKET_CONNECTION_FLAG = flag;
	}
	
}
